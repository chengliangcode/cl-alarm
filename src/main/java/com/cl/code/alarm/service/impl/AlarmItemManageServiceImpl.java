package com.cl.code.alarm.service.impl;

import com.cl.code.alarm.core.AlarmItemEntity;
import com.cl.code.alarm.service.AlarmItemManageService;
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
    public void addAlarmItem(AlarmItemEntity alarmItemEntity) {
        alarmItemRepository.addAlarmItem(alarmItemEntity);
    }

    @Override
    public void updateAlarmItem(AlarmItemEntity alarmItemEntity) {

    }

    @Override
    public void deleteAlarmItem(List<Long> alarmItemIds) {

    }

}
