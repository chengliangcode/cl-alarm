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

    public static void registerStrategy(AlarmType alarmType, AlarmStrategy strategy) {
        STRATEGY_MAP.put(alarmType.getName(), strategy);
    }

    public static AlarmStrategy getStrategy(AlarmType alarmType) {
        AlarmStrategy strategy = STRATEGY_MAP.get(alarmType.getName());
        if (strategy == null) {
            throw new IllegalArgumentException("未知的预警类型");
        }
        return strategy;
    }

}