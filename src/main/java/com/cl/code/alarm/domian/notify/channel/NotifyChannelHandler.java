package com.cl.code.alarm.domian.notify.channel;

import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.record.AlarmRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知方式处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyChannelHandler {

    public static Map<AlarmRecord, List<NotifyChannel>> execute(Map<AlarmRecord, AlarmItem> alarmRecordMap) {
        HashMap<AlarmRecord, List<NotifyChannel>> alarmRecordAndPushChannelMap = new HashMap<>(alarmRecordMap.size());
        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            alarmRecordAndPushChannelMap.put(alarmRecord, alarmItem.getNotifyChannels().getChannels());
        });
        return alarmRecordAndPushChannelMap;
    }

}
