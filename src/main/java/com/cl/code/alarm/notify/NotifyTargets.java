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

    List<NotifyTargetItem> targetItems = new ArrayList<>(5);

    public NotifyTargets(List<NotifyTargetItem> targetItems) {
        this.targetItems.addAll(targetItems);
    }

    public void addTargetItem(NotifyTargetItem notifyTargetItem) {
        this.targetItems.add(notifyTargetItem);
    }

    public List<NotifyTargetItem> getTargetItems() {
        return Collections.unmodifiableList(this.targetItems);
    }

}
