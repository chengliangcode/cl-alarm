package com.cl.code.alarm.domian.notify.target;

import java.util.Set;

/**
 * 通知目标提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface NotifyTargetProvider {

    /**
     * 得到通知目标
     *
     * @param markValue 标志值
     * @return {@code List<Long>}
     */
    default NotifyTarget getNotifyTarget(String markValue) {
        return NotifyTarget.of(getTarget(markValue));
    }

    Set<Long> getTarget(String markValue);

}
