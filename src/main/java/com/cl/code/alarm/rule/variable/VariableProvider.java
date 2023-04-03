package com.cl.code.alarm.rule.variable;

import com.cl.code.el.param.VariableParam;
import com.google.common.base.Strings;

/**
 * 变量提供者
 *
 * @author chengliang
 * @since 1.0.0
 */
public abstract class VariableProvider {

    private final String variableName;

    public VariableProvider(String variableName) {
        if (Strings.isNullOrEmpty(variableName)) {
            throw new RuntimeException("变量名不能为空");
        }
        this.variableName = variableName;
        // 注册到工厂
        boolean register = VariableFactory.register(this);
        if (!register) {
            throw new RuntimeException("变量提供者无法重复注册");
        }
    }

    /**
     * 获取变量名
     *
     * @return {@link String}
     */
    public String variableName() {
        return this.variableName;
    }

    /**
     * 获取变量值
     *
     * @param variableParam 变量参数
     * @param businessParam 业务参数
     * @return {@link VariableValue}
     */
    public abstract VariableValue getVariableValue(VariableParam variableParam, Object businessParam);

}
