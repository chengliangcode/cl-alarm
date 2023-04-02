package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.business.BusinessScope;
import com.cl.code.alarm.domain.entity.AlarmItem;

import java.util.List;

/**
 * 预警项存储库（需要依赖项目实现）
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmItemRepository {

    /**
     * 通过范围获取预警项 (生效的)
     *
     * @param businessScope 业务范围
     * @return {@link List}<{@link AlarmItem}>
     */
    List<AlarmItem> getAlarmItemByScope(BusinessScope businessScope);

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
