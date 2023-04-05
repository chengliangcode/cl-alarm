package com.cl.code.alarm.domian.item;

import com.cl.code.alarm.domian.monitor.ChangeFactors;
import com.cl.code.alarm.domian.notify.channel.NotifyChannels;
import com.cl.code.alarm.domian.notify.target.NotifyTargets;
import com.cl.code.alarm.domian.rule.AlarmRules;

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
    String getAlarmType();

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

    /**
     * 预警是否生效
     *
     * @return boolean
     */
    boolean isEnable();

}
