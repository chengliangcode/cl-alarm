package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecord;

import java.util.List;

/**
 * 预警消息提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmMessageProvider<T, V> {

    /**
     * 推送消息
     *
     * @param alarmRecord     预警记录
     * @param notifyTargetIds 通知目标id
     * @param messages        消息
     * @param t               t
     */
    void pushMessage(AlarmRecord alarmRecord, T t, List<Object> messages, NotifyTarget<V> notifyTargetIds);

}
