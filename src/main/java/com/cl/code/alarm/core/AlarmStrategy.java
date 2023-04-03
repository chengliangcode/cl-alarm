package com.cl.code.alarm.core;

import com.cl.code.alarm.rule.variable.VariableValue;
import com.cl.code.el.param.VariableParam;

/**
 * 预警策略
 * 提供参数工厂
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmStrategy {

    /**
     * 获得价值
     *
     * @param param      参数
     * @param businessId 业务标识
     * @return {@link VariableValue}
     */
    Object getValue(VariableParam param, Long businessId);

}
