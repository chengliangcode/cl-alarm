package com.cl.code.alarm.domian.service;

import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.monitor.Factor;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.util.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengliang
 * @since 1.0.0
 */
public class InnerAlarmItemService {

    private static final Log logger = LogFactory.getLog(InnerAlarmItemService.class);

    private final AlarmItemRepository alarmItemRepository;

    public InnerAlarmItemService(AlarmItemRepository alarmItemRepository) {
        this.alarmItemRepository = alarmItemRepository;
    }

    public List<AlarmItem> getAlarmItemByChangeFactor(Factor factor) {
        List<AlarmItem> alarmItems = alarmItemRepository.getAlarmItemByChangeFactor(factor);
        // 生效预警
        alarmItems = alarmItems.stream().filter(AlarmItem::isEnable).collect(Collectors.toList());
        if (CollectionUtils.isNullOrEmpty(alarmItems)) {
            logger.info("未找到监听了[" + factor.getName() + "]变更因素的预警项");
        }

        logger.info("监听变更因素[" + factor.getName() + "]的预警项" + "" + "[" + alarmItems.stream()
                .map(item -> item.getAlarmItemId().toString()).collect(Collectors.joining(",")) + "]");

        return alarmItems;
    }

}
