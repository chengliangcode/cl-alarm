package com.cl.code.alarm;

import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.infrastructure.AlarmRecordRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预警项管理服务
 *
 * @author chengliang
 * @since 1.0.0
 */
public final class AgtAlarmItemService {

    @Resource
    private AlarmItemRepository alarmItemRepository;

    @Resource
    private AlarmRecordRepository alarmRecordRepository;

    public void addAlarmItem(AlarmItem alarmItem) {
        alarmItemRepository.addAlarmItem(alarmItem);
    }

    public void updateAlarmItem(AlarmItem alarmItem) {
        // 更新预警
        AlarmItem oldAlarmItem = alarmItemRepository.getAlarmItemById(alarmItem.getAlarmItemId());
        if (oldAlarmItem == null) {
            throw new RuntimeException("预警项不存在");
        }
        if (!alarmItem.getAlarmType().equals(oldAlarmItem.getAlarmType())) {
            throw new RuntimeException("预警类型不允许修改");
        }
        //
        if (!alarmItem.getChangeFactors().equals(oldAlarmItem.getChangeFactors()) ||
                !alarmItem.getAlarmRules().equals(oldAlarmItem.getAlarmRules()) ||
                alarmItem.isEnable() != oldAlarmItem.isEnable()
        ) {
            // 预警判断规则发送变化 删除未处理预警记录
        }

        // 预警维度出现大问题

        // 删除预警字段
    }

    public void deleteAlarmItem(List<Long> alarmItemIds) {

    }

}
