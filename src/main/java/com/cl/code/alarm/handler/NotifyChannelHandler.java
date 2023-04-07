package com.cl.code.alarm.handler;

import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.channel.NotifyChannel;
import com.cl.code.alarm.domian.notify.channel.NotifyChannelProvider;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecordEntity;
import com.cl.code.alarm.infrastructure.AlarmStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 通知渠道处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyChannelHandler {

    public static <B, U, M> Map<AlarmRecordEntity, List<M>> execute(Map<AlarmRecordEntity, AlarmItem> alarmRecordMap, Function<AlarmRecordEntity, NotifyTarget<U>> f1, Function<AlarmRecordEntity, B> f2) {
        HashMap<AlarmRecordEntity, List<M>> alarmRecordAndPushChannelMap = new HashMap<>(alarmRecordMap.size());
        alarmRecordMap.forEach((alarmRecord, alarmItem) -> {
            String alarmType = alarmRecord.getAlarmType();
            AlarmStrategy<B, U, M> strategy = AlarmStrategyFactory.getStrategy(alarmRecord.getAlarmType());
            Map<NotifyChannel, NotifyChannelProvider<B, U, M>> notifyChannelProviders = strategy.getNotifyChannelProviders();
            if (notifyChannelProviders == null) {
                throw new RuntimeException("[" + alarmType + "]需要注册通知渠道提供者");
            }
            List<NotifyChannel> channels = alarmItem.getNotifyChannels().getChannels();
            List<M> messages = new ArrayList<>();
            channels.forEach(channel -> {
                NotifyChannelProvider<B, U, M> notifyChannelProvider = notifyChannelProviders.get(channel);
                if (notifyChannelProvider == null) {
                    throw new RuntimeException("[" + alarmType + "]需要注册[" + channel.getName() + "]通知渠道提供者");
                }
                List<M> message = notifyChannelProvider.buildMessage(alarmRecord, f2.apply(alarmRecord), f1.apply(alarmRecord));
                messages.addAll(message);
            });
            alarmRecordAndPushChannelMap.put(alarmRecord, messages);
        });
        return alarmRecordAndPushChannelMap;
    }

}
