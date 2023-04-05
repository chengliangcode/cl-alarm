package com.cl.code.alarm.domian.notify.target;

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
public class NotifyTargets {

    private final List<NotifyVirtualTarget> virtualTargets;

    public void addVirtualTarget(NotifyVirtualTarget notifyTargetItem) {
        if (contains(notifyTargetItem)) {
            return;
        }
        this.virtualTargets.add(notifyTargetItem);
    }

    public List<NotifyVirtualTarget> getVirtualTargets() {
        return Collections.unmodifiableList(this.virtualTargets);
    }

    public boolean contains(NotifyVirtualTarget notifyTargetItem) {
        return virtualTargets.stream().anyMatch(item -> item.getType().equals(notifyTargetItem.getType()) &&
                item.getValue().equals(notifyTargetItem.getValue())
        );
    }
}
