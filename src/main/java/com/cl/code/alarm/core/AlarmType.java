package com.cl.code.alarm.core;

import lombok.Data;

import java.util.Objects;

/**
 * 预警类型
 *
 * @author chengliang
 * @since 1.0.0
 */
@Data
public class AlarmType {

    private String name;

    public AlarmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AlarmType) {
            AlarmType alarmType = (AlarmType) o;
            return Objects.equals(getName(), alarmType.getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}
