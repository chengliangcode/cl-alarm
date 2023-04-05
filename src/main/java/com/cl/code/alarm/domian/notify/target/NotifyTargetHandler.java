package com.cl.code.alarm.domian.notify.target;

import com.cl.code.alarm.core.AlarmStrategy;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.util.CollectionUtils;
import com.google.common.collect.HashBasedTable;

import java.util.*;

/**
 * 通知对象处理
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyTargetHandler {

    /**
     * 执行
     *
     * @param alarmRecordMap 预警记录
     * @return {@link Map}<{@link AlarmRecord}, {@link List}<{@link Long}>>
     */
    public static Map<AlarmRecord, Set<Long>> execute(Map<AlarmRecord, AlarmItem> alarmRecordMap) {

        HashBasedTable<String, Long, Set<Long>> cache = HashBasedTable.create();

        Map<AlarmRecord, Set<Long>> alarmRecordAndPushTargetMap = new HashMap<>(alarmRecordMap.size());
        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            Long businessId = alarmRecord.getBusinessId();
            String alarmType = alarmRecord.getAlarmType();
            AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmType);
            // 虚拟通知对象
            List<NotifyVirtualTarget> virtualTargets = alarmItem.getNotifyTargets().getVirtualTargets();

            Set<Long> allRealTargetIds = new HashSet<>();
            virtualTargets.forEach(virtualTarget -> {
                Set<Long> realTargetIds = cache.get(virtualTarget.index(), businessId);
                if (CollectionUtils.isNullOrEmpty(realTargetIds)) {
                    // 解析通知对象
                    realTargetIds = strategy.parseNotifyTarget(virtualTarget, businessId);
                    cache.put(virtualTarget.index(), businessId, realTargetIds);
                }
                allRealTargetIds.addAll(realTargetIds);
            });
            // 设置真实通知对象
            alarmRecordAndPushTargetMap.put(alarmRecord, allRealTargetIds);
        });
        return alarmRecordAndPushTargetMap;
    }

}
