package com.cl.code.alarm.domian.notify.channel;

/**
 * 通知通道
 *
 * @author chengliang
 * @since 1.0.0
 */
@FunctionalInterface
public interface NotifyChannel {

    /**
     * 得到名字
     *
     * @return {@link String}
     */
    String getName();

}
