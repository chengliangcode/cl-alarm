package com.cl.code.alarm.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 预警策略工厂
 *
 * @author chengliang
 * @since 1.0.0
 */
public class AlarmStrategyFactory {

    private static final Map<String, AlarmStrategy> STRATEGY_MAP = new HashMap<>();

    public static void registerStrategy(String strategyName, AlarmStrategy strategy) {
        STRATEGY_MAP.put(strategyName, strategy);
    }

    public static AlarmStrategy getStrategy(String strategyName) {
        AlarmStrategy strategy = STRATEGY_MAP.get(strategyName);
        if (strategy == null) {
            throw new IllegalArgumentException("未知的预警类型");
        }
        return strategy;
    }

}