package com.cl.code.alarm.domain.service.impl;

import com.cl.code.alarm.domain.entity.AlarmItem;
import com.cl.code.alarm.domain.service.AlarmItemManageService;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chengliang
 * @since 1.0.0
 */
public class AlarmItemManageServiceImpl implements AlarmItemManageService {
    @Resource
    private AlarmItemRepository alarmItemRepository;

    @Override
    public void addAlarmItem(AlarmItem alarmItem) {
        alarmItemRepository.addAlarmItem(alarmItem);
    }

    @Override
    public void updateAlarmItem(AlarmItem alarmItem) {

    }

    @Override
    public void deleteAlarmItem(List<Long> alarmItemIds) {

    }

}
