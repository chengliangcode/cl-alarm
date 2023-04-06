package com.cl.code.alarm.domian.notify.channel;

/**
 * 通知渠道
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyChannel {

    private final String name;

    private NotifyChannel(String name) {
        this.name = name;
    }

    public static NotifyChannel of(String name) {
        return new NotifyChannel(name);
    }

    public String getName() {
        return this.name;
    }

}
