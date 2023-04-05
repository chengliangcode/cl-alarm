package com.cl.code.alarm;

import com.alibaba.fastjson2.JSON;
import com.cl.code.alarm.domian.business.BusinessChangeEvent;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.channel.NotifyChannel;
import com.cl.code.alarm.domian.notify.channel.NotifyChannelHandler;
import com.cl.code.alarm.domian.notify.target.NotifyTargetHandler;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.domian.record.AlarmRecordHandler;
import com.cl.code.alarm.domian.rule.AlarmRuleHandler;
import com.cl.code.alarm.domian.service.InnerAlarmItemService;
import com.cl.code.alarm.domian.service.InnerAlarmRecordService;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.infrastructure.AlarmMessageProvider;
import com.cl.code.alarm.infrastructure.AlarmRecordRepository;
import com.cl.code.alarm.infrastructure.IdGenerator;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.SnowFlakeGenerator;
import com.cl.code.alarm.util.UnmodifiableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Resource
    private AlarmMessageProvider alarmMessageProvider;

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
    public void trigger(BusinessChangeEvent event) {

        InnerAlarmItemService innerAlarmItemService = new InnerAlarmItemService(alarmItemRepository);
        InnerAlarmRecordService innerAlarmRecordService = new InnerAlarmRecordService(alarmRecordRepository, idGenerator);

        logger.info("触发业务变更事件,变更因素[" + event.getFactor().getName() + "] 参数:" + JSON.toJSONString(event.getBusinessIds().get()));

        // 获取预警项
        List<AlarmItem> alarmItems = innerAlarmItemService.getAlarmItemByChangeFactor(event.getFactor());
        if (CollectionUtils.isNullOrEmpty(alarmItems)) {
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
        Map<AlarmRecord, AlarmItem> alarmRecordMap = AlarmRecordHandler.execute(effectAlarmItems, unHandleAlarmRecords);

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
        Map<AlarmRecord, Set<Long>> alarmRecordAndPushTargetMap = NotifyTargetHandler.execute(alarmRecordMap);

        // 通知方式
        Map<AlarmRecord, List<NotifyChannel>> alarmRecordAndPushChannelMap = NotifyChannelHandler.execute(alarmRecordMap);

        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            Set<Long> targetIds = alarmRecordAndPushTargetMap.get(alarmRecord);
            List<NotifyChannel> channels = alarmRecordAndPushChannelMap.get(alarmRecord);
            // 创建和推送消息
            if (!CollectionUtils.isNullOrEmpty(targetIds) && !CollectionUtils.isNullOrEmpty(channels)) {
                logger.info("创建和推送消息,预警记录[" + alarmRecord.getAlarmRecordId() + "],通知目标:" + JSON.toJSONString(targetIds) + ",通知方式:" + JSON.toJSONString(channels));
                alarmMessageProvider.createAndPushMessage(alarmRecord, channels, targetIds);
            }

        });
    }
}
