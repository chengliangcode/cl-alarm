package com.cl.code.alarm.domian.record;

import com.cl.code.alarm.domian.item.AlarmItem;
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
     *
     */
    private AlarmItem alarmItem;

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

    public AlarmRecordEntity(Long alarmRecordId, AlarmItem alarmItem, Long businessId, Long alarmTime) {
        this.alarmRecordId = alarmRecordId;
        this.alarmItem = alarmItem;
        this.businessId = businessId;
        this.alarmTime = alarmTime;
    }

    @Override
    public Long getAlarmItemId() {
        return this.alarmItem.getAlarmItemId();
    }

    @Override
    public String getAlarmType() {
        return this.alarmItem.getAlarmType();
    }

    @Override
    public boolean isHandle() {
        return this.handle;
    }
}
