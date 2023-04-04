package com.cl.code.alarm.record;

import com.cl.code.alarm.core.AlarmItem;
import lombok.Data;


@Data
public class AlarmRecordEntity implements AlarmRecord {

    /**
     * 预警记录id
     */
    private Long alarmRecordId;

    /**
     * 预警项id
     */
    private Long alarmItemId;

    /**
     * 业务标识
     */
    private Long businessId;

    /**
     * 预警时间
     */
    private Long alarmTime;

    /**
     * 预警类型
     */
    private String alarmType;

    /**
     * 分组标识
     */
    private String groupTag;

    /**
     * json
     */
    private String json;

    public AlarmRecordEntity(Long alarmRecordId, AlarmItem alarmItem, Long businessId, Long alarmTime) {
        if (alarmRecordId == null) {
            throw new IllegalArgumentException("预警记录id不能为空");
        }
        this.alarmRecordId = alarmRecordId;
        this.alarmItemId = alarmItem.getAlarmItemId();
        this.alarmType = alarmItem.getAlarmType().getName();
        this.businessId = businessId;
        this.alarmTime = alarmTime;
    }

}
