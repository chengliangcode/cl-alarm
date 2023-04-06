package com.cl.code.alarm.handler;

import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.record.AlarmOtherInfo;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.infrastructure.AlarmStrategy;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.UnmodifiableList;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预警记录处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class AlarmRecordHandler {

    /**
     * 执行
     *
     * @param effectAlarmItems 效果预警项目
     */
    public static <T, V> Map<AlarmRecordEntity, AlarmItem> execute(Map<AlarmItem, UnmodifiableList<Long>> effectAlarmItems, List<AlarmRecord> unprocessedAlarmRecords) {

        long alarmTime = Instant.now().toEpochMilli();

        Map<AlarmRecordEntity, AlarmItem> recordMap = new HashMap<>(effectAlarmItems.size());
        for (Map.Entry<AlarmItem, UnmodifiableList<Long>> entry : effectAlarmItems.entrySet()) {
            AlarmItem alarmItem = entry.getKey();
            UnmodifiableList<Long> businessIds = entry.getValue();

            for (Long businessId : businessIds) {
                // 判断是否已经存在
                Optional<AlarmRecord> optional = unprocessedAlarmRecords.stream().filter(alarmRecord -> alarmRecord.getBusinessId().equals(businessId) && alarmRecord.getAlarmItemId().equals(alarmItem.getAlarmItemId())).findFirst();

                AlarmRecordEntity record;
                if (optional.isPresent()) {
                    AlarmRecord alarmRecord = optional.get();
                    // 移除
                    unprocessedAlarmRecords.remove(alarmRecord);
                    record = buildRecord(alarmRecord.getAlarmRecordId(), alarmItem, businessId, alarmTime);
                } else {
                    record = buildRecord(null, alarmItem, businessId, alarmTime);
                }
                recordMap.put(record, alarmItem);
            }
        }
        return recordMap;
    }

    /**
     * 执行
     *
     * @param alarmRecordMap 预警记录地图
     * @return {@link Map}<{@link AlarmRecordEntity}, {@link T}>
     */
    public static <T, V> Map<AlarmRecordEntity, T> execute(Map<AlarmRecordEntity, AlarmItem> alarmRecordMap) {
        Map<AlarmRecordEntity, T> recordMap = new HashMap<>(alarmRecordMap.size());
        for (Map.Entry<AlarmRecordEntity, AlarmItem> entry : alarmRecordMap.entrySet()) {
            AlarmRecordEntity alarmRecord = entry.getKey();
            AlarmItem alarmItem = entry.getValue();
            AlarmStrategy<T, V> strategy = AlarmStrategyFactory.getStrategy(alarmItem.getAlarmType());
            AlarmOtherInfo<T> otherInfo = strategy.getOtherInfo(alarmItem, alarmRecord.getBusinessId());
            if (otherInfo != null) {
                alarmRecord.setGroupTag(otherInfo.getGroupTag());
                alarmRecord.setJson(otherInfo.getJson());
                recordMap.put(alarmRecord, otherInfo.getInfo());
            } else {
                recordMap.put(alarmRecord, null);
            }
        }
        return recordMap;
    }

    public static List<AlarmRecord> filterAutoUpdateStatusRecord(List<AlarmRecord> unprocessedAlarmRecords) {
        if (CollectionUtils.isNullOrEmpty(unprocessedAlarmRecords)) {
            return Collections.emptyList();
        }
        // 第二次不满足自动变为已处理状态
        return unprocessedAlarmRecords.stream().filter(record -> AlarmStrategyFactory.getStrategy(record.getAlarmType()).isAutoUpdateRecordStatus()).collect(Collectors.toList());
    }

    private static AlarmRecordEntity buildRecord(Long alarmRecordId, AlarmItem alarmItem, Long businessId, Long alarmTime) {
        return new AlarmRecordEntity(alarmRecordId, alarmItem.getAlarmItemId(), alarmItem.getAlarmType(), businessId, alarmTime);
    }

}
