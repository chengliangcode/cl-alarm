package com.cl.code.alarm.domian.item;

import com.cl.code.alarm.domian.monitor.ChangeFactors;
import com.cl.code.alarm.domian.notify.channel.NotifyChannels;
import com.cl.code.alarm.domian.notify.target.NotifyTargets;
import com.cl.code.alarm.domian.rule.AlarmRules;
import lombok.Data;

/**
 * 预警项
 *
 * @author chengliang
 * @since 1.0.0
 */
@Data
public class AlarmItemEntity implements AlarmItem {

    /**
     * 预警项id
     */
    private final Long alarmItemId;

    /**
     * 预警类型
     */
    private final String alarmType;

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

    /**
     * 是否启用
     */
    private boolean enable;

    public AlarmItemEntity(Long alarmItemId, String alarmType, boolean enable) {
        if (alarmItemId == null) {
            throw new IllegalArgumentException("预警项id不能为空");
        }
        if (alarmType == null) {
            throw new IllegalArgumentException("预警类型不能为空");
        }
        this.alarmType = alarmType;
        this.alarmItemId = alarmItemId;
        this.enable = enable;
    }

}
