package com.cl.code.alarm.domian.rule.variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 变量工厂
 *
 * @author chengliang
 * @since 1.0.0
 */
public class VariableFactory {

    private static final Map<String, VariableProvider> VARIABLE_MAP = new ConcurrentHashMap<>(20);

    static {
        // 初始化变量提供器
        // 在此处注册您的变量提供器
        // 例如：VariableFactory.register(new CustomVariableProvider());
    }

    /**
     * 注册变量提供器
     *
     * @param provider 变量提供器对象
     * @return 如果注册成功，返回 true；否则返回 false
     */
    public static synchronized boolean register(VariableProvider provider) {
        // 检查是否已经注册
        if (VARIABLE_MAP.containsKey(provider.variableName())) {
            return false;
        }
        // 注册变量提供器
        VARIABLE_MAP.put(provider.variableName(), provider);
        return true;
    }

    /**
     * 根据变量名称获取对应的变量提供器对象
     *
     * @param name 变量名称
     * @return 如果找到对应的变量提供器对象，返回该对象；否则返回 null
     */
    public static synchronized VariableProvider getVariableProvider(String name) {
        return VARIABLE_MAP.get(name);
    }

    /**
     * 获取所有已注册的变量提供器对象列表
     *
     * @return 所有已注册的变量提供器对象列表
     */
    public static List<VariableProvider> allVariableProviders() {
        return Collections.unmodifiableList(new ArrayList<>(VARIABLE_MAP.values()));
    }

}