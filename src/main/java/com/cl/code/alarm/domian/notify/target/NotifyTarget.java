package com.cl.code.alarm.domian.notify.target;

import com.cl.code.alarm.domian.notify.mark.NotifyMark;
import com.cl.code.alarm.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;

public class NotifyTarget {

    private NotifyMark notifyMark;

    private final Set<Long> targets;

    private NotifyTarget(Set<Long> targets) {
        this.targets = targets;
    }

    public static NotifyTarget of(Set<Long> targets) {
        return new NotifyTarget(targets);
    }

    public static NotifyTarget of(Long target) {
        return new NotifyTarget(Collections.singleton(target));
    }

    public static NotifyTarget empty() {
        return new NotifyTarget(null);
    }

    public Set<Long> getTargets() {
        return this.targets;
    }

    public boolean isEmpty() {
        return CollectionUtils.isNullOrEmpty(targets);
    }

    public void setNotifyMark(NotifyMark notifyMark) {
        this.notifyMark = notifyMark;
    }

    public NotifyMark getNotifyMark() {
        return this.notifyMark;
    }

}
