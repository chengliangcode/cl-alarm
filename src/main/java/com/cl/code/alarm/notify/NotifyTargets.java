package com.cl.code.alarm.notify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通知目标
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyTargets {

    List<NotifyTargetItem> notifyTargetItems = new ArrayList<>(5);

    public NotifyTargets(List<NotifyTargetItem> notifyTargetItems) {
        this.notifyTargetItems.addAll(notifyTargetItems);
    }

    public void addNotifyTargetItem(NotifyTargetItem notifyTargetItem) {
        this.notifyTargetItems.add(notifyTargetItem);
    }

    public List<NotifyTargetItem> getNotifyTargetItems() {
        return Collections.unmodifiableList(this.notifyTargetItems);
    }

}
