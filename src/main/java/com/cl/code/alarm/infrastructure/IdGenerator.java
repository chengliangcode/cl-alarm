package com.cl.code.alarm.infrastructure;

@FunctionalInterface
public interface IdGenerator {

    /**
     * 生成id
     *
     * @return {@link Long}
     */
    Long generateId();

}
