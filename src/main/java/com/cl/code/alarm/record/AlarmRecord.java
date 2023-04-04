package com.cl.code.alarm.record;

import com.cl.code.alarm.core.AlarmType;

/**
 * 预警记录
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmRecord {

    Long getAlarmRecordId();

    Long getAlarmItemId();

    Long getBusinessId();

    Long getAlarmTime();

    AlarmType getAlarmType();

    String getGroupTag();

    String getJson();

}
