package com.cl.code.alarm.domian.service;

import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.record.AlarmRecord;
import com.cl.code.alarm.infrastructure.AlarmRecordRepository;
import com.cl.code.alarm.infrastructure.IdGenerator;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.UnmodifiableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 内部预警记录处理
 *
 * @author chengliang
 * @since 1.0.0
 */
public class InnerAlarmRecordService {
    private static final Log logger = LogFactory.getLog(InnerAlarmRecordService.class);

    private final AlarmRecordRepository alarmRecordRepository;

    private final IdGenerator idGenerator;

    public InnerAlarmRecordService(AlarmRecordRepository alarmRecordRepository, IdGenerator idGenerator) {
        this.alarmRecordRepository = alarmRecordRepository;
        this.idGenerator = idGenerator;
    }

    public List<AlarmRecord> getUnHandleAlarmRecords(List<AlarmItem> alarmItems, UnmodifiableList<Long> businessIds) {
        List<AlarmRecord> unHandleAlarmRecords = new ArrayList<>();
        alarmItems.forEach(alarmItem ->
                businessIds.forEach(businessId -> {
                    List<AlarmRecord> alarmRecords = alarmRecordRepository.getRecordByAlarmItemAndBusinessId(alarmItem.getAlarmItemId(), businessId);
                    if (!CollectionUtils.isNullOrEmpty(alarmRecords)) {
                        alarmRecords = alarmRecords.stream()
                                .filter(alarmRecord -> !alarmRecord.isHandle())
                                .collect(Collectors.toList());
                        unHandleAlarmRecords.addAll(alarmRecords);
                    }
                }));
        if (!CollectionUtils.isNullOrEmpty(unHandleAlarmRecords)) {
            logger.info("已经存在的预警记录" + unHandleAlarmRecords.stream().map(item -> item.getAlarmRecordId().toString()).collect(Collectors.joining(",")));
        }
        return unHandleAlarmRecords;

    }


    public void handleAlarmRecords(List<AlarmRecord> alarmRecords) {
        if (!CollectionUtils.isNullOrEmpty(alarmRecords)) {
            logger.info("置为已处理预警记录" + alarmRecords.stream().map(item -> item.getAlarmRecordId().toString()).collect(Collectors.joining(",")));
            alarmRecordRepository.handleRecord(alarmRecords);
        }
    }

    public void saveOrUpdateAlarmRecords(Collection<AlarmRecord> alarmRecords) {

        List<AlarmRecord> saveList = alarmRecords.stream()
                .filter(alarmRecord -> Objects.isNull(alarmRecord.getAlarmRecordId()))
                .collect(Collectors.toList());

        List<AlarmRecord> updateList = alarmRecords.stream()
                .filter(alarmRecord -> !Objects.isNull(alarmRecord.getAlarmRecordId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isNullOrEmpty(saveList)) {
            saveList.forEach(item -> item.setAlarmRecordId(idGenerator.generateId()));
            logger.info("新增预警记录" + "[" + saveList.stream()
                    .map(alarmRecord -> alarmRecord.getAlarmRecordId().toString()).collect(Collectors.joining(",")) + "]");
            alarmRecordRepository.saveRecord(saveList);

        }
        if (!CollectionUtils.isNullOrEmpty(updateList)) {
            logger.info("更新预警记录" + "[" + updateList.stream()
                    .map(alarmRecord -> alarmRecord.getAlarmRecordId().toString()).collect(Collectors.joining(",")) + "]");
            alarmRecordRepository.updateRecord(updateList);
        }
    }


}
