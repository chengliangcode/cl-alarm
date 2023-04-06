package com.cl.code.alarm.core;

import com.cl.code.alarm.infrastructure.AlarmStrategy;

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

    public static void registerStrategy(String alarmType, AlarmStrategy strategy) {
        STRATEGY_MAP.put(alarmType, strategy);
    }

    public static <T, V> AlarmStrategy<T, V> getStrategy(String alarmType) {
        AlarmStrategy<T, V> strategy = STRATEGY_MAP.get(alarmType);
        if (strategy == null) {
            throw new IllegalArgumentException("未知的预警类型[" + alarmType + "],请实现该预警类型的策略类" + AlarmStrategy.class + "并注册到" + AlarmStrategyFactory.class + "中");
        }
        return strategy;
    }

}