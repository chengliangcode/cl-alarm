package com.cl.code.alarm.domian.item;

import com.cl.code.alarm.domian.monitor.ChangeFactors;
import com.cl.code.alarm.domian.notify.channel.NotifyChannels;
import com.cl.code.alarm.domian.notify.target.NotifyTargets;
import com.cl.code.alarm.domian.notify.target.NotifyVirtualTarget;
import com.cl.code.alarm.domian.rule.AlarmRules;
import com.cl.code.el.expression.base.BooleanExpression;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;
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

    private final NotifyTargets notifyTargets = new NotifyTargets(new ArrayList<>());

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

    @Nonnull
    public AlarmItemBuilder addFactor(String... factors) {
        for (String factor : factors) {
            if (factor != null) {
                this.changeFactors.addFactor(() -> factor);
            }
        }
        return this;
    }

    @Nonnull
    public AlarmItemBuilder addRule(BooleanExpression... expressions) {
        for (BooleanExpression expression : expressions) {
            if (expression != null) {
                this.alarmRules.addRule(expression);
            }
        }
        return this;
    }

    @Nonnull
    public AlarmItemBuilder addVirtualTarget(String type, String value) {
        if (!Strings.isNullOrEmpty(type) && !Strings.isNullOrEmpty(value)) {
            this.notifyTargets.addVirtualTarget(new NotifyVirtualTarget() {
                @Override
                public String getType() {
                    return type;
                }

                @Override
                public String getValue() {
                    return value;
                }
            });
        }
        return this;
    }

    @Nonnull
    public AlarmItemBuilder addChannel(String... channels) {
        for (String channel : channels) {
            if (channel != null) {
                this.notifyChannels.addChannel(() -> channel);
            }
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
        alarmItem.setNotifyTargets(this.notifyTargets);
        alarmItem.setNotifyChannels(this.notifyChannels);
        return alarmItem;
    }


}
