package com.cl.code.alarm.domian.rule.variable;

import com.cl.code.el.param.VariableValue;

/**
 * 变量提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
@FunctionalInterface
public interface VariableProvider {

    /**
     * 获取变量值
     *
     * @return {@link VariableValue}
     */
    Object getValue(Long businessId);

    /**
     * 获取变量值
     *
     * @return {@link VariableValue}
     */
    default VariableValue getVariableValue(Long businessId) {
        return VariableValue.of(getValue(businessId));
    }


}
