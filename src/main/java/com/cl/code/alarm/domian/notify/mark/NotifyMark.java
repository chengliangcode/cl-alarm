package com.cl.code.alarm.domian.notify.mark;

/**
 * 通知标识
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface NotifyMark {

    /**
     * 类型
     *
     * @return {@code String}
     */
    NotifyMarkType getType();

    /**
     * 值
     *
     * @return {@code String}
     */
    String getValue();

    /**
     * 标识
     *
     * @return {@link String}
     */
    default String index() {
        return getType().getName() + "-" + getValue();
    }

}
