package com.cl.code.alarm.domian.record;

import lombok.Data;

import java.util.List;


/**
 * 预警记录实体
 *
 * @author chengliang
 * @since 1.0.0
 */
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
     * 预警类型
     */
    private String alarmType;

    /**
     * 业务标识
     */
    private Long businessId;

    /**
     * 预警时间
     */
    private Long alarmTime;

    /**
     * 分组标识
     */
    private String groupTag;

    /**
     * json
     */
    private String json;

    /**
     * 通知目标ids
     */
    private List<Long> notifyTargetIds;

    /**
     * 处理
     */
    private boolean handle;

    public AlarmRecordEntity(Long alarmRecordId, Long alarmItemId, String alarmType, Long businessId, Long alarmTime) {
        this.alarmRecordId = alarmRecordId;
        this.alarmItemId = alarmItemId;
        this.alarmType = alarmType;
        this.businessId = businessId;
        this.alarmTime = alarmTime;
        this.handle = false;
    }

}
