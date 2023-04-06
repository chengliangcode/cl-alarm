package com.cl.code.alarm.domian.notify.channel;

import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecord;

import java.util.List;

/**
 * 通知通道提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface NotifyChannelProvider<T, V> {

    /**
     * 构建消息
     *
     * @param alarmRecord   预警记录
     * @param info          信息
     * @param notifyTargets 通知目标
     * @return {@link List}<{@link Object}>
     */
    List<Object> buildMessage(AlarmRecord alarmRecord, T info, NotifyTarget<V> notifyTargets);

}
