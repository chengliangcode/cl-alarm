package com.cl.code.alarm;

import com.cl.code.alarm.business.BusinessChangeEvent;
import com.cl.code.alarm.core.AlarmItem;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.infrastructure.AlarmRecordRepository;
import com.cl.code.alarm.infrastructure.IdGenerator;
import com.cl.code.alarm.record.AlarmRecord;
import com.cl.code.alarm.record.AlarmRecordHandler;
import com.cl.code.alarm.rule.AlarmRuleHandler;
import com.cl.code.alarm.util.SnowFlakeGenerator;
import com.cl.code.alarm.util.UnmodifiableList;
import com.google.common.collect.Iterables;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 业务预警服务
 *
 * @author chengliang
 * @since 1.0.0
 */
public class BusinessAlarmTrigger {

    private final AlarmItemRepository alarmItemRepository;

    private final AlarmRecordRepository alarmRecordRepository;

    private final IdGenerator idGenerator;

    public BusinessAlarmTrigger(AlarmItemRepository alarmItemRepository, AlarmRecordRepository alarmRecordRepository) {
        this(alarmItemRepository, alarmRecordRepository, new SnowFlakeGenerator(1, 1));
    }

    public BusinessAlarmTrigger(AlarmItemRepository alarmItemRepository, AlarmRecordRepository alarmRecordRepository, IdGenerator idGenerator) {
        this.alarmItemRepository = alarmItemRepository;
        this.alarmRecordRepository = alarmRecordRepository;
        this.idGenerator = idGenerator;
    }

    /**
     * 触发
     *
     * @param event 事件
     */
    public void trigger(BusinessChangeEvent event) {

        List<AlarmItem> alarmItemEntities = alarmItemRepository.getAlarmItemByChangeFactor(event.getFactor());

        if (Iterables.isEmpty(alarmItemEntities)) {
            return;
        }

        // 得出生效的规则
        Map<AlarmItem, UnmodifiableList<Long>> effectAlarmItems = AlarmRuleHandler.execute(alarmItemEntities, event.getBusinessIds());
        // 预警记录
        List<AlarmRecord> alarmRecords = AlarmRecordHandler.execute(effectAlarmItems, idGenerator);

        Map<Long, AlarmItem> effectAlarmMap = effectAlarmItems.keySet().stream().collect(Collectors.toMap(AlarmItem::getAlarmItemId, Function.identity()));
        // 保存记录
        alarmRecordRepository.saveRecord(alarmRecords);

        // 获取通知目标


    }
}
