package com.cl.code.alarm.core;

import com.cl.code.alarm.monitor.ChangeFactors;
import com.cl.code.alarm.notify.NotifyChannels;
import com.cl.code.alarm.notify.NotifyTargets;
import com.cl.code.alarm.rule.AlarmRules;
import lombok.Data;

/**
 * 预警项
 *
 * @author chengliang
 * @since 1.0.0
 */
@Data
public class AlarmItem {

    /**
     * 预警项id
     */
    private final Long alarmItemId;

    /**
     * 预警类型
     */
    private final AlarmType alarmType;

    /**
     * 变化因素
     */
    private ChangeFactors changeFactors;

    /**
     * 预警规则
     */
    private AlarmRules alarmRules;

    /**
     * 预警通知目标
     */
    private NotifyTargets notifyTargets;

    /**
     * 通知方式
     */
    private NotifyChannels notifyChannels;


    public AlarmItem(Long alarmItemId, AlarmType alarmType) {
        this.alarmType = alarmType;
        if (alarmItemId == null) {
            throw new IllegalArgumentException("预警项id不能为空");
        }
        this.alarmItemId = alarmItemId;
    }

}
