package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.record.AlarmRecord;

import java.util.List;

/**
 * 预警记录存储库
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmRecordRepository {

    void saveRecord(List<AlarmRecord> alarmRecords);

    AlarmRecord getRecord(Long alarmItemId, Long businessId);

}
