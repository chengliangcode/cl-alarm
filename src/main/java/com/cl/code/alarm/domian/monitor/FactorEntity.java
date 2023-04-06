package com.cl.code.alarm.domian.monitor;

public class FactorEntity implements Factor {

    private final String name;

    public FactorEntity(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
