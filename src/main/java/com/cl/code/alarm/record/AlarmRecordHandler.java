package com.cl.code.alarm.record;

import com.cl.code.alarm.core.AlarmItem;
import com.cl.code.alarm.core.AlarmStrategy;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.infrastructure.IdGenerator;
import com.cl.code.alarm.util.UnmodifiableList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public static List<AlarmRecord> execute(Map<AlarmItem, UnmodifiableList<Long>> effectAlarmItems, IdGenerator idGenerator) {

        long alarmTime = Instant.now().toEpochMilli();

        List<AlarmRecord> records = new ArrayList<>();
        for (Map.Entry<AlarmItem, UnmodifiableList<Long>> entry : effectAlarmItems.entrySet()) {
            AlarmItem alarmItem = entry.getKey();
            UnmodifiableList<Long> businessIds = entry.getValue();
            AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmItem.getAlarmType());
            Map<Long, RecordSupplement> appends = strategy.recordAppend(alarmItem, businessIds);

            if (Objects.equals(appends.size(), businessIds.size())) {
                throw new RuntimeException("预警记录补充异常，补充数量与业务数量不一致");
            }

            for (Long businessId : businessIds) {
                AlarmRecord record = buildRecord(idGenerator.generateId(), alarmItem, businessId, appends.get(businessId), alarmTime);
                records.add(record);
            }
        }
        return records;
    }

    public static AlarmRecord buildRecord(Long alarmRecordId, AlarmItem alarmItem, Long businessId, RecordSupplement supplement, Long alarmTime) {
        AlarmRecordEntity alarmRecord = new AlarmRecordEntity(alarmRecordId, alarmItem, businessId, alarmTime);
        if (supplement == null) {
            supplement = RecordSupplement.def(alarmItem);
        }
        alarmRecord.setGroupTag(supplement.getGroupTag());
        alarmRecord.setJson(supplement.getJson());
        return alarmRecord;
    }


}
