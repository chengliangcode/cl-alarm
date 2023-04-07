package com.cl.code.alarm.domian.notify.target;

import com.cl.code.alarm.domian.notify.mark.NotifyMark;

import java.util.List;
import java.util.Set;

/**
 * 通知目标提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface NotifyTargetProvider<U> {

    /**
     * 得到通知目标
     *
     * @param notifyMarks 通知标识
     * @param businessId  业务标识
     * @return {@link NotifyTarget}<{@link U}>
     */
    default NotifyTarget<U> getNotifyTarget(List<NotifyMark> notifyMarks, Long businessId) {
        return NotifyTarget.of(getTarget(notifyMarks, businessId));
    }

    /**
     * 获取目标
     *
     * @param notifyMarks 通知标识
     * @param businessId  业务标识
     * @return {@link Set}<{@link U}>
     */
    Set<U> getTarget(List<NotifyMark> notifyMarks, Long businessId);

}
