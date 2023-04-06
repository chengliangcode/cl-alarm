package com.cl.code.alarm.domian.notify.mark;

import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NotifyMarkType {

    private final String name;

    private NotifyMarkType(String name) {
        if (Strings.isNullOrEmpty(name)) {
            throw new RuntimeException("通知标识类型名称不能为空");
        }
        this.name = name;
    }

    public static NotifyMarkType of(String name) {
        return new NotifyMarkType(name);
    }

    public String getName() {
        return this.name;
    }
}
