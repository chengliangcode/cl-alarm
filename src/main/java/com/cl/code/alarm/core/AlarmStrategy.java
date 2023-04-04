package com.cl.code.alarm.core;

import com.cl.code.alarm.notify.NotifyVirtualTarget;
import com.cl.code.alarm.record.RecordSupplement;
import com.cl.code.alarm.rule.variable.VariableValue;
import com.cl.code.alarm.util.UnmodifiableList;
import com.cl.code.el.param.VariableParam;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 预警策略
 * 提供参数工厂
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmStrategy {

    /**
     * 获取值
     *
     * @param param      参数
     * @param businessId 业务标识
     * @return {@link VariableValue}
     */
    Object getValue(VariableParam param, Long businessId);

    /**
     * 添加记录
     *
     * @param businessIds 业务标识
     * @param alarmItem   预警项
     * @return {@code Map<Long, RecordSupplement>}
     */
    default Map<Long, RecordSupplement> recordAppend(AlarmItem alarmItem, UnmodifiableList<Long> businessIds) {
        return businessIds.stream().collect(Collectors.toMap(Function.identity(), businessId -> recordAppend(alarmItem, businessId)));
    }

    /**
     * 添加记录
     *
     * @param businessId 业务标识
     * @param alarmItem  预警项
     * @return {@code RecordSupplement}
     */
    default RecordSupplement recordAppend(AlarmItem alarmItem, Long businessId) {
        return RecordSupplement.def(alarmItem);
    }

    /**
     * 解析通知目标
     *
     * @param businessId   业务标识
     * @param notifyTarget 通知目标
     * @return {@code List<Long>}
     */
    List<Long> parseNotifyTarget(NotifyVirtualTarget notifyTarget, Long businessId);

}
