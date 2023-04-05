package com.cl.code.alarm.domian.notify.channel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * 通知方式
 *
 * @author chengliang
 * @since 1.0.0
 */
@EqualsAndHashCode
@AllArgsConstructor
public class NotifyChannels {

    private final List<NotifyChannel> channels;

    public void addChannel(NotifyChannel channel) {
        if (contains(channel)) {
            return;
        }
        this.channels.add(channel);
    }

    public List<NotifyChannel> getChannels() {
        return Collections.unmodifiableList(this.channels);
    }

    public boolean contains(NotifyChannel channel) {
        return channels.stream().anyMatch(item -> item.getName().equals(channel.getName()));
    }
}
