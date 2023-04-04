package com.cl.code.alarm.notify;

import com.cl.code.alarm.core.AlarmItem;
import com.cl.code.alarm.core.AlarmStrategy;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.record.AlarmRecord;
import com.cl.code.alarm.util.UnmodifiableList;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Iterables;

import java.util.List;
import java.util.Map;

/**
 * 通知目标处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyTargetHandler {

    /**
     * 执行
     *
     * @param alarmRecords     预警记录
     * @param effectAlarmMap   生效预警Map
     * @param effectAlarmItems 生效预警项
     * @return {@code Map<AlarmRecord, List<Long>>}
     */
    public static Map<AlarmRecord, List<Long>> execute(List<AlarmRecord> alarmRecords, Map<Long, AlarmItem> effectAlarmMap, Map<AlarmItem, UnmodifiableList<Long>> effectAlarmItems) {

        // 同一个业务id 只发一次
        // 同target.type target.value businessId 就是同一个target
        HashBasedTable<String, Long, List<Long>> cache = HashBasedTable.create();

        alarmRecords.forEach(alarmRecord -> {
            AlarmItem alarmItem = effectAlarmMap.get(alarmRecord.getAlarmItemId());
            UnmodifiableList<Long> businessIds = effectAlarmItems.get(alarmItem);
            AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmItem.getAlarmType());

            List<NotifyVirtualTarget> virtualTargets = alarmItem.getNotifyTargets().getTargetItems();
            businessIds.forEach(businessId -> {
                virtualTargets.forEach(virtualTarget -> {
                    List<Long> realTargetIds = cache.get(virtualTarget.index(), businessId);
                    if (Iterables.isEmpty(realTargetIds)) {
                        realTargetIds = strategy.parseNotifyTarget(virtualTarget, businessId);
                        cache.put(virtualTarget.index(), businessId, realTargetIds);
                    }

                });
            });

        });

    }

}
