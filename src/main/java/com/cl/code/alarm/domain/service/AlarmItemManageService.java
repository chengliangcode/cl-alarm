package com.cl.code.alarm.domain.service;

import com.cl.code.alarm.domain.entity.AlarmItem;

import java.util.List;

/**
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmItemManageService {

    /**
     * 添加预警项
     *
     * @param alarmItem 预警项
     */
    void addAlarmItem(AlarmItem alarmItem);

    /**
     * 更新预警项
     *
     * @param alarmItem 预警项
     */
    void updateAlarmItem(AlarmItem alarmItem);

    /**
     * 删除预警项
     *
     * @param alarmItemIds 预警项id
     */
    void deleteAlarmItem(List<Long> alarmItemIds);

}
