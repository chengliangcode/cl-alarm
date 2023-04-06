package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;

import java.util.List;

/**
 * 预警记录存储库
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmRecordRepository {

    /**
     * 得到记录通过预警id
     *
     * @param alarmItemId 预警项id
     * @param businessId  业务标识
     * @return {@link List}<{@link AlarmRecord}>
     */
    List<AlarmRecordEntity> getRecordByAlarmItemAndBusinessId(Long alarmItemId, Long businessId);

    /**
     * 保存记录
     *
     * @param alarmRecords 预警记录
     */
    void saveRecord(List<AlarmRecordEntity> alarmRecords);

    /**
     * 更新记录
     *
     * @param alarmRecords 预警记录
     */
    void updateRecord(List<AlarmRecordEntity> alarmRecords);

    /**
     * 删除通过预警项id
     *
     * @param alarmItemId 预警项id
     */
    void deleteByAlarmItemId(Long alarmItemId);

    /**
     * 处理记录
     *
     * @param alarmRecords 预警记录
     */
    void handleRecord(List<AlarmRecord> alarmRecords);

}
