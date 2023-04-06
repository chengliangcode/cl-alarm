package com.cl.code.alarm.domian.notify.target;

import com.cl.code.alarm.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;

/**
 * 通知目标
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyTarget<V> {

    private final Set<V> targets;

    private NotifyTarget(Set<V> targets) {
        this.targets = targets;
    }

    public static <V> NotifyTarget<V> of(Set<V> targets) {
        return new NotifyTarget<>(targets);
    }

    public static <V> NotifyTarget<V> of(V target) {
        return new NotifyTarget<>(Collections.singleton(target));
    }

    public static <V> NotifyTarget<V> empty() {
        return new NotifyTarget<>(null);
    }

    public Set<V> getTargets() {
        return this.targets;
    }

    public boolean isEmpty() {
        return CollectionUtils.isNullOrEmpty(targets);
    }

}
