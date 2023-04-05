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
     * 得到预警项通过id
     *
     * @param alarmItemId 预警项id
     * @return {@code AlarmItem}
     */
    AlarmItem getAlarmItemById(Long alarmItemId);

    /**
     * 得到预警项通过变动因素
     *
     * @param factor 变动因素
     * @return {@code List<AlarmItem>}
     */
    List<AlarmItem> getAlarmItemByChangeFactor(Factor factor);

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
