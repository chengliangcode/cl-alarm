package com.cl.code.alarm.domian.item;

import com.cl.code.alarm.domian.monitor.ChangeFactors;
import com.cl.code.alarm.domian.notify.channel.NotifyChannels;
import com.cl.code.alarm.domian.notify.mark.NotifyMarks;
import com.cl.code.alarm.domian.rule.AlarmRules;

/**
 * 预警项
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmItem {

    /**
     * 获取预警项id
     *
     * @return {@code Long}
     */
    Long getAlarmItemId();

    /**
     * 获取预警类型
     *
     * @return {@code AlarmType}
     */
    String getAlarmType();

    /**
     * 获取变化因素
     *
     * @return {@code ChangeFactors}
     */
    ChangeFactors getChangeFactors();

    /**
     * 获取预警规则
     *
     * @return {@code AlarmRules}
     */
    AlarmRules getAlarmRules();

    /**
     * 获取通知标识
     *
     * @return {@code NotifyMarks}
     */
    NotifyMarks getNotifyMarks();

    /**
     * 获取通知渠道
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
