package com.cl.code.alarm.domian.monitor;

/**
 * 因素
 *
 * @author chengliang
 * @since 1.0.0
 */
@FunctionalInterface
public interface Factor {

    /**
     * 得到名字
     *
     * @return {@link String}
     */
    String getName();

}

