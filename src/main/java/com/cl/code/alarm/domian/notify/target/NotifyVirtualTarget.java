package com.cl.code.alarm.domian.notify.target;

/**
 * 通知目标
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface NotifyVirtualTarget {

    /**
     * 得到类型
     *
     * @return {@code String}
     */
    String getType();

    /**
     * 获得值
     *
     * @return {@code String}
     */
    String getValue();

    /**
     * 指数
     *
     * @return {@link String}
     */
    default String index() {
        return getValue() + "-" + getType();
    }

}
