package com.cl.code.alarm.domian.notify.mark;

import com.google.common.base.Strings;

public class NotifyMarkEntity implements NotifyMark {

    private final NotifyMarkType notifyMarkType;

    private final String value;

    public NotifyMarkEntity(String type, String value) {
        this(NotifyMarkType.of(type), value);
    }

    public NotifyMarkEntity(NotifyMarkType notifyMarkType, String value) {
        if (Strings.isNullOrEmpty(value)) {
            throw new RuntimeException("值不能为空");
        }
        this.notifyMarkType = notifyMarkType;
        this.value = value;
    }

    @Override
    public NotifyMarkType getType() {
        return this.notifyMarkType;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
