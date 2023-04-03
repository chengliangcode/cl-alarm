package com.cl.code.alarm.monitor;


import com.google.common.base.Strings;

/**
 * 变动因素
 *
 * @author chengliang
 * @since 1.0.0
 */
public class ChangeFactor {

    private final String name;

    public ChangeFactor(String name) {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("变动因素名称不能为空");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
