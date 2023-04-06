package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.domian.notify.channel.NotifyChannel;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecord;

import java.util.List;

/**
 * 预警消息提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmMessageProvider {

    /**
     * 创建和推送消息
     *
     * @param alarmRecord     预警记录
     * @param channels        方式
     * @param notifyTargetIds 通知目标id
     */
    void createAndPushMessage(AlarmRecord alarmRecord, List<NotifyChannel> channels, List<NotifyTarget> notifyTargetIds);

}
