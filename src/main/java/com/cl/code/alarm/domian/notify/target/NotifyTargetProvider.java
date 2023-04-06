package com.cl.code.alarm.domian.notify.target;

import com.cl.code.alarm.domian.record.RecordSupplement;

import java.util.Set;

/**
 * 通知目标提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface NotifyTargetProvider<T> {

    /**
     * 得到通知目标
     *
     * @param markValue        标志值
     * @param recordSupplement
     * @return {@code List<Long>}
     */
    default NotifyTarget getNotifyTarget(String markValue, T recordSupplement) {
        return NotifyTarget.of(getTarget(markValue, recordSupplement));
    }

    Set<Long> getTarget(String markValue, T recordSupplement);

}
