package com.cl.code.alarm;

import com.alibaba.fastjson2.JSON;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.business.BusinessChangeEvent;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.domian.service.InnerAlarmItemService;
import com.cl.code.alarm.domian.service.InnerAlarmRecordService;
import com.cl.code.alarm.handler.AlarmRecordHandler;
import com.cl.code.alarm.handler.AlarmRuleHandler;
import com.cl.code.alarm.handler.NotifyChannelHandler;
import com.cl.code.alarm.handler.NotifyMarkHandler;
import com.cl.code.alarm.infrastructure.*;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.SnowFlakeGenerator;
import com.cl.code.alarm.util.UnmodifiableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务预警服务
 *
 * @author chengliang
 * @since 1.0.0
 */
public final class AgtAlarmService {

    private static final Log logger = LogFactory.getLog(AgtAlarmService.class);


    @Resource
    private AlarmItemRepository alarmItemRepository;

    @Resource
    private AlarmRecordRepository alarmRecordRepository;

    private final IdGenerator idGenerator;

    public AgtAlarmService(IdGenerator idGenerator) {
        if (idGenerator == null) {
            this.idGenerator = new SnowFlakeGenerator(1, 1);
        } else {
            this.idGenerator = idGenerator;
        }
    }

    /**
     * 触发
     *
     * @param event 事件
     */
    public <T, V> void trigger(BusinessChangeEvent event) {
        InnerAlarmItemService innerAlarmItemService = new InnerAlarmItemService(alarmItemRepository);
        InnerAlarmRecordService innerAlarmRecordService = new InnerAlarmRecordService(alarmRecordRepository, idGenerator);

        logger.info("触发业务变更事件,变更因素" + JSON.toJSONString(event.getFactors()) + " 参数:" + JSON.toJSONString(event.getBusinessIds().get()));

        // 获取预警项
        List<AlarmItem> alarmItems = innerAlarmItemService.getAlarmItemByChangeFactor(event.getFactors());
        if (CollectionUtils.isNullOrEmpty(alarmItems)) {
            logger.info("没有监听" + JSON.toJSONString(event.getFactors()) + "因素的预警项");
            return;
        }
        // 生效预警
        Map<AlarmItem, UnmodifiableList<Long>> effectAlarmItems = AlarmRuleHandler.execute(alarmItems, event.getBusinessIds());
        if (CollectionUtils.isNullOrEmpty(effectAlarmItems)) {
            logger.info("没有生效的预警项");
        } else {
            effectAlarmItems.forEach((alarmItem, businessIds) -> {
                logger.info("预警项[" + alarmItem.getAlarmItemId() + "]生效,businessId:" + JSON.toJSONString(businessIds.get()));
            });
        }

        // 获取已经存在的预警记录
        List<AlarmRecord> unHandleAlarmRecords = innerAlarmRecordService.getUnHandleAlarmRecords(alarmItems, event.getBusinessIds());

        // 生成预警记录
        Map<AlarmRecordEntity, AlarmItem> alarmRecordMap = AlarmRecordHandler.execute(effectAlarmItems, unHandleAlarmRecords);

        Map<AlarmRecordEntity, T> alarmRecordInfoMap = AlarmRecordHandler.execute(alarmRecordMap);

        // 业务自动已处理逻辑
        unHandleAlarmRecords = AlarmRecordHandler.filterAutoUpdateStatusRecord(unHandleAlarmRecords);

        // 记录变为已处理
        innerAlarmRecordService.handleAlarmRecords(unHandleAlarmRecords);

        if (CollectionUtils.isNullOrEmpty(alarmRecordMap)) {
            return;
        }
        // 保存记录更新
        innerAlarmRecordService.saveOrUpdateAlarmRecords(alarmRecordMap.keySet());

        // 通知目标
        Map<AlarmRecordEntity, NotifyTarget<V>> alarmRecordAndPushTargetMap = NotifyMarkHandler.execute(alarmRecordMap);

        // 通知方式
        Map<AlarmRecordEntity, List<Object>> alarmRecordAndPushChannelMap = NotifyChannelHandler.execute(alarmRecordMap, alarmRecordAndPushTargetMap::get, alarmRecordInfoMap::get);

        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            NotifyTarget<V> targets = alarmRecordAndPushTargetMap.get(alarmRecord);
            List<Object> messages = alarmRecordAndPushChannelMap.get(alarmRecord);
            T t = alarmRecordInfoMap.get(alarmRecord);
            AlarmStrategy<T, V> strategy = AlarmStrategyFactory.getStrategy(alarmRecord.getAlarmType());
            AlarmMessageProvider<T, V> alarmMessageProvider = strategy.getAlarmMessageProvider();
            if (alarmMessageProvider != null) {
                // 创建和推送消息
                if (targets != null && !targets.isEmpty() && !CollectionUtils.isNullOrEmpty(messages)) {
                    logger.info("创建和推送消息,预警记录[" + alarmRecord.getAlarmRecordId() + "],通知目标:" + JSON.toJSONString(targets) + ",通知内容:" + JSON.toJSONString(messages));
                    alarmMessageProvider.pushMessage(alarmRecord, t, messages, targets);
                }
            }
        });
    }
}
