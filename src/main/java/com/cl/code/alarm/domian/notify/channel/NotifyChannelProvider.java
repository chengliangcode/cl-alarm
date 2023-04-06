package com.cl.code.alarm.domian.notify.channel;

import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.record.AlarmRecord;

import java.util.List;

public interface NotifyChannelProvider {

    Object handlerChannel(AlarmRecord alarmRecord, List<NotifyTarget> notifyTargets);

}
