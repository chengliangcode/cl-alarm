package com.cl.code.alarm;

import com.cl.code.alarm.business.BusinessChangeEvent;
import com.cl.code.alarm.business.BusinessScope;
import com.cl.code.alarm.domain.entity.AlarmItem;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.variable.VariableFactory;
import com.cl.code.alarm.variable.VariableProvider;
import com.cl.code.alarm.variable.VariableValue;
import com.cl.code.el.expression.base.StringExpression;
import com.cl.code.el.param.VariableParam;
import com.google.common.collect.Iterables;

import java.util.List;
import java.util.Set;

/**
 * 业务预警服务
 *
 * @author chengliang
 * @since 1.0.0
 */
public class BusinessAlarmTrigger {

    private final AlarmItemRepository alarmItemRepository;

    public BusinessAlarmTrigger(AlarmItemRepository alarmItemRepository) {
        this.alarmItemRepository = alarmItemRepository;
    }

    /**
     * 触发
     *
     * @param businessChangeEvent 业务更改事件
     */
    public void trigger(BusinessChangeEvent businessChangeEvent) {
        BusinessScope businessScope = businessChangeEvent.getBusinessScope();
        // 查找对于预警范围的预警
        List<AlarmItem> alarmItems = alarmItemRepository.getAlarmItemByScope(businessScope);
        if (alarmItems == null || alarmItems.size() == 0) {
            // 无对应范围预警
            return;
        }
        // 获取该预警所需条件(向依赖者获取)
        for (AlarmItem alarmItem : alarmItems) {
            List<StringExpression<?>> expressions = alarmItem.getExpressions();
            // 判断是否满足条件
            for (StringExpression<?> expression : expressions) {
                Set<VariableParam> variableParams = expression.getVariableParams();
                if (!Iterables.isEmpty(variableParams)) {
                    // 有变量参数
                    for (VariableParam variableParam : variableParams) {

                        VariableProvider variableProvider = VariableFactory.getVariableProvider(variableParam.getName());
                        VariableValue variableValue = variableProvider.getVariableValue(variableParam, businessChangeEvent.getBusinessIndex());
                    }
                }
            }
        }
    }

}
