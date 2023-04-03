package com.cl.code.alarm;

import com.cl.code.alarm.business.BusinessChangeEvent;
import com.cl.code.alarm.core.AlarmItem;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.rule.RuleHandler;
import com.google.common.collect.Iterables;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务预警服务
 *
 * @author chengliang
 * @since 1.0.0
 */
public class BusinessAlarmTrigger {

    private final AlarmItemRepository alarmItemRepository;

    public BusinessAlarmTrigger(AlarmItemRepository alarmItemRepository) {
        this.alarmItemRepository = alarmItemRepository;
    }

    /**
     * 触发
     *
     * @param event 事件
     */
    public void trigger(BusinessChangeEvent event) {

        List<AlarmItem> alarmItems = alarmItemRepository.getAlarmItemByChangeFactor(event.getFactor());

        // 过滤生效的预警项
        alarmItems = alarmItems.stream()
                .filter(item -> !Iterables.isEmpty(item.getNotifyTargets().getTargetItems()))
                .filter(item -> !Iterables.isEmpty(item.getNotifyChannels().getChannels()))
                .collect(Collectors.toList());

        List<Long> businessIds = event.getBusinessIds();

        RuleHandler.execute(alarmItems, businessIds);


    }

}
