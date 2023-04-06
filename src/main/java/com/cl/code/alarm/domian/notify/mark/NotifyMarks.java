package com.cl.code.alarm.domian.notify.mark;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * 通知目标
 *
 * @author chengliang
 * @since 1.0.0
 */
@EqualsAndHashCode
@AllArgsConstructor
public class NotifyMarks {

    private final List<NotifyMark> notifyMarks;

    public void addNotifyMark(NotifyMark notifyMark) {
        if (contains(notifyMark)) {
            return;
        }
        this.notifyMarks.add(notifyMark);
    }

    public List<NotifyMark> getNotifyMarks() {
        return Collections.unmodifiableList(this.notifyMarks);
    }

    public boolean contains(NotifyMark notifyMark) {
        return notifyMarks.stream().anyMatch(item -> item.getType().equals(notifyMark.getType()) && item.getValue().equals(notifyMark.getValue()));
    }

}
