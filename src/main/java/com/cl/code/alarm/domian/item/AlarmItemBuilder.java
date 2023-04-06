package com.cl.code.alarm.domian.item;

import com.cl.code.alarm.domian.monitor.ChangeFactors;
import com.cl.code.alarm.domian.monitor.Factor;
import com.cl.code.alarm.domian.monitor.FactorEntity;
import com.cl.code.alarm.domian.notify.channel.NotifyChannel;
import com.cl.code.alarm.domian.notify.channel.NotifyChannels;
import com.cl.code.alarm.domian.notify.mark.NotifyMarkEntity;
import com.cl.code.alarm.domian.notify.mark.NotifyMarkType;
import com.cl.code.alarm.domian.notify.mark.NotifyMarks;
import com.cl.code.alarm.domian.rule.AlarmRules;
import com.cl.code.el.expression.base.BooleanExpression;

import java.util.ArrayList;

/**
 * 预警项构建器
 *
 * @author chengliang
 * @since 1.0.0
 */
public class AlarmItemBuilder {

    /**
     * 预警项id
     */
    private final Long alarmItemId;

    private final String alarmType;

    private final ChangeFactors changeFactors = new ChangeFactors(new ArrayList<>());

    private final AlarmRules alarmRules = new AlarmRules(new ArrayList<>());

    private final NotifyMarks notifyMarks = new NotifyMarks(new ArrayList<>());

    private final NotifyChannels notifyChannels = new NotifyChannels(new ArrayList<>());

    private boolean enable;

    private AlarmItemBuilder(Long alarmItemId, String alarmType) {
        this.alarmItemId = alarmItemId;
        this.alarmType = alarmType;
        this.enable = true;
    }

    public static AlarmItemBuilder create(Long alarmItemId, String alarmType) {
        return new AlarmItemBuilder(alarmItemId, alarmType);
    }

    public AlarmItemBuilder addFactor(String factor) {
        if (factor != null) {
            return addFactor(new FactorEntity(factor));
        }
        return this;
    }

    public AlarmItemBuilder addFactor(Factor factor) {
        if (factor != null) {
            this.changeFactors.addFactor(factor);
        }
        return this;
    }

    public AlarmItemBuilder addRule(BooleanExpression... expressions) {
        for (BooleanExpression expression : expressions) {
            if (expression != null) {
                this.alarmRules.addRule(expression);
            }
        }
        return this;
    }

    public AlarmItemBuilder addNotifyMark(NotifyMarkType type, String value) {
        this.notifyMarks.addNotifyMark(new NotifyMarkEntity(type, value));
        return this;
    }

    public AlarmItemBuilder addNotifyChannel(String notifyChannel) {
        if (notifyChannel != null) {
            return addNotifyChannel(NotifyChannel.of(notifyChannel));
        }
        return this;
    }

    public AlarmItemBuilder addNotifyChannel(NotifyChannel notifyChannel) {
        if (notifyChannel != null) {
            this.notifyChannels.addChannel(notifyChannel);
        }
        return this;
    }

    public AlarmItemBuilder enable() {
        this.enable = true;
        return this;
    }

    public AlarmItemBuilder disable() {
        this.enable = false;
        return this;
    }

    public AlarmItem build() {
        AlarmItemEntity alarmItem = new AlarmItemEntity(alarmItemId, alarmType, enable);
        alarmItem.setChangeFactors(this.changeFactors);
        alarmItem.setAlarmRules(this.alarmRules);
        alarmItem.setNotifyMarks(this.notifyMarks);
        alarmItem.setNotifyChannels(this.notifyChannels);
        return alarmItem;
    }

}
