package com.cl.code.alarm.handler;

import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.channel.NotifyChannel;
import com.cl.code.alarm.domian.notify.channel.NotifyChannelProvider;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.infrastructure.AlarmStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知渠道处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyChannelHandler {

    public static Map<AlarmRecordEntity, List<NotifyChannel>> execute(Map<AlarmRecordEntity, AlarmItem> alarmRecordMap, Map<AlarmRecordEntity, List<NotifyTarget>> alarmRecordAndPushTargetMap) {
        HashMap<AlarmRecordEntity, List<NotifyChannel>> alarmRecordAndPushChannelMap = new HashMap<>(alarmRecordMap.size());
        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            String alarmType = alarmRecord.getAlarmType();
            List<NotifyTarget> notifyTargets = alarmRecordAndPushTargetMap.get(alarmRecord);
            AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmRecord.getAlarmType());
            Map<NotifyChannel, NotifyChannelProvider> notifyChannelProviders = strategy.getNotifyChannelProviders();
            if (notifyChannelProviders == null) {
                throw new RuntimeException("[" + alarmType + "]需要注册通知渠道提供者");
            }
            List<NotifyChannel> channels = alarmItem.getNotifyChannels().getChannels();
            channels.forEach(channel -> {
                NotifyChannelProvider notifyChannelProvider = notifyChannelProviders.get(channel);
                if (notifyChannelProvider == null) {
                    throw new RuntimeException("[" + alarmType + "]需要注册[" + channel.getName() + "]通知渠道提供者");
                }
                notifyChannelProvider.handlerChannel(alarmRecord, notifyTargets);
            });
            alarmRecordAndPushChannelMap.put(alarmRecord, alarmItem.getNotifyChannels().getChannels());
        }); return alarmRecordAndPushChannelMap;
    }

}
