package com.cl.code.alarm.handler;

import com.alibaba.fastjson2.JSON;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.mark.NotifyMark;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.notify.target.NotifyTargetProvider;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.infrastructure.AlarmStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知对象处理
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyMarkHandler {

    private static final Log logger = LogFactory.getLog(NotifyMarkHandler.class);


    /**
     * 执行
     *
     * @param alarmRecordMap 预警记录
     * @return {@code Map<AlarmRecordEntity, List<NotifyTarget>>}
     */
    public static <B, U, M> Map<AlarmRecordEntity, NotifyTarget<U>> execute(Map<AlarmRecordEntity, AlarmItem> alarmRecordMap) {


        Map<AlarmRecordEntity, NotifyTarget<U>> alarmRecordAndPushTargetMap = new HashMap<>(alarmRecordMap.size());
        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            Long businessId = alarmRecord.getBusinessId();
            String alarmType = alarmRecord.getAlarmType();
            AlarmStrategy<B, U, M> strategy = AlarmStrategyFactory.getStrategy(alarmType);
            NotifyTargetProvider<U> notifyTargetProvider = strategy.getNotifyTargetProvider();
            if (notifyTargetProvider == null) {
                throw new RuntimeException("[" + alarmType + "]需要注册通知对象提供者");
            }

            // 通知标记
            List<NotifyMark> notifyMarks = alarmItem.getNotifyMarks().getNotifyMarks();

            NotifyTarget<U> notifyTarget = notifyTargetProvider.getNotifyTarget(notifyMarks, businessId);
            if (notifyTarget != null && !notifyTarget.isEmpty()) {
                alarmRecordAndPushTargetMap.put(alarmRecord, notifyTarget);
                logger.info("预警记录[" + alarmRecord + "]的真实通知对象为" + JSON.toJSONString(notifyTarget.getTargets()));
            }
        });
        // 设置真实通知对象
        return alarmRecordAndPushTargetMap;
    }


}
