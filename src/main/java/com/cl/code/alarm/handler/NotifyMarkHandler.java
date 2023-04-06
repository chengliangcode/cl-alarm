package com.cl.code.alarm.handler;

import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.mark.NotifyMark;
import com.cl.code.alarm.domian.notify.mark.NotifyMarkType;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.notify.target.NotifyTargetProvider;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.infrastructure.AlarmStrategy;
import com.google.common.collect.HashBasedTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

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
    public static Map<AlarmRecordEntity, List<NotifyTarget>> execute(Map<AlarmRecordEntity, AlarmItem> alarmRecordMap) {

        HashBasedTable<String, Long, NotifyTarget> cache = HashBasedTable.create();

        Map<AlarmRecordEntity, List<NotifyTarget>> alarmRecordAndPushTargetMap = new HashMap<>(alarmRecordMap.size());
        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            Long businessId = alarmRecord.getBusinessId();
            String alarmType = alarmRecord.getAlarmType();
            AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmType);
            Map<NotifyMarkType, NotifyTargetProvider> notifyTargetProviders = strategy.getNotifyTargetProviders();

            // 通知标记
            List<NotifyMark> notifyMarks = alarmItem.getNotifyMarks().getNotifyMarks();

            List<NotifyTarget> allTargetIds = new ArrayList<>(notifyMarks.size());
            notifyMarks.forEach(notifyMark -> {
                NotifyTarget notifyTarget;
                if (cache.contains(notifyMark.index(), businessId)) {
                    notifyTarget = cache.get(notifyMark.index(), businessId);
                    if (notifyTarget == null) {
                        notifyTarget = NotifyTarget.empty();
                    }
                } else {
                    if (notifyTargetProviders == null) {
                        throw new RuntimeException("[" + alarmType + "]需要注册通知对象提供者");
                    }
                    NotifyTargetProvider notifyTargetProvider = notifyTargetProviders.get(notifyMark.getType());
                    if (notifyTargetProvider == null) {
                        throw new RuntimeException("[" + alarmType + "]需要注册[" + notifyMark.getType().getName() + "]通知对象提供者");
                    }

                    notifyTarget = notifyTargetProvider.getNotifyTarget(notifyMark.getValue());
                    if (notifyTarget == null) {
                        notifyTarget = NotifyTarget.empty();
                    }
                    notifyTarget.setNotifyMark(notifyMark);
                    cache.put(notifyMark.index(), businessId, notifyTarget);
                }
                if (!notifyTarget.isEmpty()) {
                    allTargetIds.add(notifyTarget);
                }
            });
            // 设置真实通知对象
            logger.info("预警记录[" + alarmRecord + "]的真实通知对象为[" + allTargetIds + "]");
            alarmRecordAndPushTargetMap.put(alarmRecord, allTargetIds);
        });
        return alarmRecordAndPushTargetMap;
    }



}
