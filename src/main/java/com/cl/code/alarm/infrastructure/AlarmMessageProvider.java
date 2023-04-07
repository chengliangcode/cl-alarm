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
public interface AlarmMessageProvider<B, U, M> {

    /**
     * 推送消息
     *
     * @param alarmRecord     预警记录
     * @param info            信息
     * @param messages        消息
     * @param notifyTargetIds 通知目标id
     */
    void pushMessage(AlarmRecord alarmRecord, B info, List<M> messages, NotifyTarget<U> notifyTargetIds);

}
