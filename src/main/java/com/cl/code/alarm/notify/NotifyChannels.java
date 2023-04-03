package com.cl.code.alarm.notify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通知方式
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyChannels {

    private final List<NotifyChannel> channels = new ArrayList<>(3);

    public NotifyChannels(List<NotifyChannel> channels) {
        this.channels.addAll(channels);
    }

    public void addChannel(NotifyChannel channel) {
        this.channels.add(channel);
    }

    public List<NotifyChannel> getChannels() {
        return Collections.unmodifiableList(this.channels);
    }
}
