package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.monitor.Factor;
import com.cl.code.alarm.util.UnmodifiableList;

import java.util.List;

/**
 * 预警项存储库（需要依赖项目实现）
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmItemRepository {

    /**
     * 获取预警项
     *
     * @param alarmItemId 预警项id
     * @return {@code AlarmItem}
     */
    AlarmItem getAlarmItemById(Long alarmItemId);

    /**
     * 获取预警项
     *
     * @param factor 因素
     * @return {@code List<AlarmItem>}
     */
    List<AlarmItem> getAlarmItemByFactor(Factor factor);

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
    void deleteAlarmItem(UnmodifiableList<Long> alarmItemIds);

}
