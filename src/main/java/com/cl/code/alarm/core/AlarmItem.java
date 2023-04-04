package com.cl.code.alarm.core;

import com.cl.code.alarm.monitor.ChangeFactors;
import com.cl.code.alarm.notify.NotifyChannels;
import com.cl.code.alarm.notify.NotifyTargets;
import com.cl.code.alarm.rule.AlarmRules;

/**
 * 预警项
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmItem {

    /**
     * 得到预警项id
     *
     * @return {@code Long}
     */
    Long getAlarmItemId();

    /**
     * 得到预警类型
     *
     * @return {@code AlarmType}
     */
    AlarmType getAlarmType();

    /**
     * 得到变化因素
     *
     * @return {@code ChangeFactors}
     */
    ChangeFactors getChangeFactors();

    /**
     * 得到预警规则
     *
     * @return {@code AlarmRules}
     */
    AlarmRules getAlarmRules();

    /**
     * 得到通知目标
     *
     * @return {@code NotifyTargets}
     */
    NotifyTargets getNotifyTargets();

    /**
     * 得到通知渠道
     *
     * @return {@code NotifyChannels}
     */
    NotifyChannels getNotifyChannels();

}
