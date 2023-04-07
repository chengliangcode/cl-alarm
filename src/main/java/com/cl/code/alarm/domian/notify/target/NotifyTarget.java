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
public class NotifyTarget<U> {

    private final Set<U> targets;

    private NotifyTarget(Set<U> targets) {
        this.targets = targets;
    }

    public static <U> NotifyTarget<U> of(Set<U> targets) {
        return new NotifyTarget<>(targets);
    }

    public static <U> NotifyTarget<U> of(U target) {
        return new NotifyTarget<>(Collections.singleton(target));
    }

    public static <U> NotifyTarget<U> empty() {
        return new NotifyTarget<>(null);
    }

    public Set<U> getTargets() {
        return this.targets;
    }

    public boolean isEmpty() {
        return CollectionUtils.isNullOrEmpty(targets);
    }

}
