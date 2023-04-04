package com.cl.code.alarm.notify;


import com.google.common.base.Strings;

/**
 * 通知目标项
 *
 * @author chengliang
 * @since 1.0.0
 */
public class NotifyTargetItem implements NotifyVirtualTarget {

    /**
     * 目标类型
     */
    private final String type;

    /**
     * 目标值
     */
    private final String value;

    public NotifyTargetItem(String type, String value) {
        if (Strings.isNullOrEmpty(type) || Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("通知目标项的类型和值不能为空");
        }
        this.type = type;
        this.value = value;
    }

    public NotifyTargetItem of(String type, String value) {
        return new NotifyTargetItem(type, value);
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
