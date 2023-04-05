package com.cl.code.alarm.domian.rule;

import com.cl.code.alarm.core.AlarmStrategy;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.UnmodifiableList;
import com.cl.code.el.ExpressionExecutor;
import com.cl.code.el.expression.base.BooleanExpression;
import com.cl.code.el.param.VariableParam;
import com.google.common.collect.HashBasedTable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 规则处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class AlarmRuleHandler {

    public static Map<AlarmItem, UnmodifiableList<Long>> execute(List<AlarmItem> alarmItems, UnmodifiableList<Long> businessIds) {
        if (CollectionUtils.isNullOrEmpty(alarmItems) || CollectionUtils.isNullOrEmpty(businessIds)) {
            return Collections.emptyMap();
        }

        // 每个预警项 都要执行每个业务id
        Map<String, List<AlarmItem>> collect = alarmItems.stream().collect(Collectors.groupingBy(AlarmItem::getAlarmType));

        Map<AlarmItem, UnmodifiableList<Long>> item = new HashMap<>(alarmItems.size());
        for (Map.Entry<String, List<AlarmItem>> entry : collect.entrySet()) {
            Map<AlarmItem, UnmodifiableList<Long>> executeResultMap = execute(entry.getKey(), entry.getValue(), businessIds);

            // 过滤数据
            for (Map.Entry<AlarmItem, UnmodifiableList<Long>> executeResult : executeResultMap.entrySet()) {
                if (!CollectionUtils.isNullOrEmpty(executeResult.getValue())) {
                    item.put(executeResult.getKey(), executeResult.getValue());
                }
            }

        }
        return item;
    }

    private static Map<AlarmItem, UnmodifiableList<Long>> execute(String alarmType, List<AlarmItem> alarmItems, UnmodifiableList<Long> businessIds) {

        AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmType);

        // 缓存参数
        HashBasedTable<VariableParam, Long, Object> cache = HashBasedTable.create();
        return alarmItems.stream().collect(Collectors.toMap(Function.identity(), alarmItem -> {
            List<BooleanExpression> ruleList = alarmItem.getAlarmRules().getRuleList();
            return new UnmodifiableList<>(businessIds.stream().filter(businessId -> ruleList.stream().allMatch(booleanExpression -> {
                Set<VariableParam> variableParams = booleanExpression.getVariableParams();
                Map<VariableParam, Object> params = new HashMap<>(2);
                if (!CollectionUtils.isNullOrEmpty(variableParams)) {

                    for (VariableParam variableParam : variableParams) {
                        Object variableValue;
                        if (cache.contains(variableParam, businessId)) {
                            variableValue = cache.get(variableParam, businessId);
                        } else {
                            Optional<?> variableValueOptional = strategy.getVariableValue(variableParam, businessId);
                            if (variableValueOptional.isPresent()) {
                                variableValue = variableValueOptional.get();
                                cache.put(variableParam, businessId, variableValue);
                            } else {
                                return false;
                            }
                        }
                        params.put(variableParam, variableValue);
                    }
                }
                return ExpressionExecutor.execute(booleanExpression, params);
            })).collect(Collectors.toList()));
        }));
    }

}
