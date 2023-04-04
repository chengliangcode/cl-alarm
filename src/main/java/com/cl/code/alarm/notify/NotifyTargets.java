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

    private final List<NotifyVirtualTarget> targetItems = new ArrayList<>(5);

    public NotifyTargets(List<NotifyVirtualTarget> targetItems) {
        this.targetItems.addAll(targetItems);
    }

    public void addTargetItem(NotifyVirtualTarget notifyTargetItem) {
        this.targetItems.add(notifyTargetItem);
    }

    public List<NotifyVirtualTarget> getTargetItems() {
        return Collections.unmodifiableList(this.targetItems);
    }

}
