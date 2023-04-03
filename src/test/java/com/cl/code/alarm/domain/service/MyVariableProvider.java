package com.cl.code.alarm.domain.service;

import com.cl.code.alarm.rule.variable.VariableProvider;
import com.cl.code.alarm.rule.variable.VariableValue;
import com.cl.code.el.param.VariableParam;

/**
 * @author chengliang
 * @since 1.0.0
 */
public class MyVariableProvider extends VariableProvider {

    public MyVariableProvider() {
        super("合同台账金额");
    }

    @Override
    public VariableValue getVariableValue(VariableParam variableParam, Object businessParam) {
        return null;
    }

}
