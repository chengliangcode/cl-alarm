package com.cl.code.alarm.service;

import com.cl.code.alarm.core.AlarmItemEntity;

import java.util.List;

/**
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmItemManageService {

    /**
     * 添加预警项
     *
     * @param alarmItemEntity 预警项
     */
    void addAlarmItem(AlarmItemEntity alarmItemEntity);

    /**
     * 更新预警项
     *
     * @param alarmItemEntity 预警项
     */
    void updateAlarmItem(AlarmItemEntity alarmItemEntity);

    /**
     * 删除预警项
     *
     * @param alarmItemIds 预警项id
     */
    void deleteAlarmItem(List<Long> alarmItemIds);

}
