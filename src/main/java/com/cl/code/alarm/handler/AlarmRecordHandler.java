package com.cl.code.alarm.handler;

import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.domian.record.RecordSupplement;
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
    public static Map<AlarmRecordEntity, AlarmItem> execute(Map<AlarmItem, UnmodifiableList<Long>> effectAlarmItems, List<AlarmRecord> unprocessedAlarmRecords) {

        long alarmTime = Instant.now().toEpochMilli();

        Map<AlarmRecordEntity, AlarmItem> recordMap = new HashMap<>(effectAlarmItems.size());
        for (Map.Entry<AlarmItem, UnmodifiableList<Long>> entry : effectAlarmItems.entrySet()) {
            AlarmItem alarmItem = entry.getKey();
            UnmodifiableList<Long> businessIds = entry.getValue();
            AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmItem.getAlarmType());
            Map<Long, RecordSupplement> appends = strategy.recordAppend(alarmItem, businessIds);

            if (!Objects.equals(appends.size(), businessIds.size())) {
                throw new RuntimeException("预警记录补充异常，补充数量与业务数量不一致");
            }

            for (Long businessId : businessIds) {
                // 判断是否已经存在
                Optional<AlarmRecord> optional = unprocessedAlarmRecords.stream().filter(alarmRecord -> alarmRecord.getBusinessId().equals(businessId) && alarmRecord.getAlarmItemId().equals(alarmItem.getAlarmItemId())).findFirst();

                AlarmRecordEntity record;
                if (optional.isPresent()) {
                    AlarmRecord alarmRecord = optional.get();
                    // 移除
                    unprocessedAlarmRecords.remove(alarmRecord);
                    record = buildRecord(alarmRecord.getAlarmRecordId(), alarmItem, businessId, appends.get(businessId), alarmTime);
                } else {
                    record = buildRecord(null, alarmItem, businessId, appends.get(businessId), alarmTime);
                }
                recordMap.put(record, alarmItem);
            }
        }
        return recordMap;
    }

    public static AlarmRecordEntity buildRecord(Long alarmRecordId, AlarmItem alarmItem, Long businessId, RecordSupplement supplement, Long alarmTime) {
        AlarmRecordEntity alarmRecord = new AlarmRecordEntity(alarmRecordId, alarmItem.getAlarmItemId(), alarmItem.getAlarmType(), businessId, alarmTime);
        if (supplement == null) {
            supplement = RecordSupplement.def(alarmItem);
        }
        alarmRecord.setInfo(supplement.getInfo());
        alarmRecord.setGroupTag(supplement.getGroupTag());
        alarmRecord.setJson(supplement.getJson());
        return alarmRecord;
    }

    public static List<AlarmRecord> filterAutoUpdateStatusRecord(List<AlarmRecord> unprocessedAlarmRecords) {
        if (CollectionUtils.isNullOrEmpty(unprocessedAlarmRecords)) {
            return Collections.emptyList();
        }
        // 第二次不满足自动变为已处理状态
        return unprocessedAlarmRecords.stream().filter(record -> AlarmStrategyFactory.getStrategy(record.getAlarmType()).isAutoUpdateRecordStatus()).collect(Collectors.toList());
    }

}
