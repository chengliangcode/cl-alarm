package com.cl.code.alarm.core;

/**
 * 预警类型
 *
 * @author chengliang
 * @since 1.0.0
 */
@FunctionalInterface
public interface AlarmType {

    /**
     * 名字
     *
     * @return {@link String}
     */
    String getName();

}
