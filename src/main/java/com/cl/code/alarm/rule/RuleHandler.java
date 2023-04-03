package com.cl.code.alarm.rule;

import com.cl.code.alarm.core.AlarmItem;
import com.cl.code.alarm.core.AlarmStrategy;
import com.cl.code.alarm.core.AlarmStrategyFactory;
import com.cl.code.alarm.core.AlarmType;
import com.cl.code.el.ExpressionExecutor;
import com.cl.code.el.expression.base.Expression;
import com.cl.code.el.param.VariableParam;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Iterables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 规则处理程序
 *
 * @author chengliang
 * @since 1.0.0
 */
public class RuleHandler {

    public static void execute(List<AlarmItem> alarmItems, List<Long> businessIds) {
        if (Iterables.isEmpty(alarmItems) || Iterables.isEmpty(businessIds)) {
            return;
        }
        // 每个预警项 都要执行每个业务id
        Map<AlarmType, List<AlarmItem>> collect = alarmItems.stream().collect(Collectors.groupingBy(AlarmItem::getAlarmType));

        for (Map.Entry<AlarmType, List<AlarmItem>> entry : collect.entrySet()) {
            Map<AlarmItem, List<Long>> execute = execute(entry.getKey(), entry.getValue(), businessIds);
            System.out.println(execute);
        }

    }

    private static Map<AlarmItem, List<Long>> execute(AlarmType alarmType, List<AlarmItem> alarmItems, List<Long> businessIds) {

        AlarmStrategy strategy = AlarmStrategyFactory.getStrategy(alarmType.getName());

        // 缓存参数
        HashBasedTable<VariableParam, Long, Object> cache = HashBasedTable.create();
        return alarmItems.stream().collect(Collectors.toMap(Function.identity(), alarmItem -> {
            List<Expression<Boolean>> ruleList = alarmItem.getAlarmRules().getRuleList();
            return businessIds.stream().filter(businessId -> ruleList.stream().allMatch(booleanExpression -> {
                Set<VariableParam> variableParams = booleanExpression.getVariableParams();
                Map<VariableParam, Object> params = new HashMap<>(2);
                if (!Iterables.isEmpty(variableParams)) {
                    // 获取参数
                    variableParams.forEach(variableParam -> {
                        Object value;
                        if (cache.contains(variableParam, businessId)) {
                            value = cache.get(variableParam, businessId);
                        } else {
                            value = strategy.getValue(variableParam, businessId);
                            cache.put(variableParam, businessId, value);
                        }
                        params.put(variableParam, value);
                    });
                }
                return ExpressionExecutor.execute(booleanExpression, params);
            })).collect(Collectors.toList());
        }));
    }

}
