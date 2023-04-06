package com.cl.code.alarm.infrastructure;

import com.cl.code.alarm.domian.item.AlarmItem;
import com.cl.code.alarm.domian.notify.channel.NotifyChannel;
import com.cl.code.alarm.domian.notify.channel.NotifyChannelProvider;
import com.cl.code.alarm.domian.notify.target.NotifyTarget;
import com.cl.code.alarm.domian.notify.target.NotifyTargetProvider;
import com.cl.code.alarm.domian.record.AlarmOtherInfo;
import com.cl.code.alarm.domian.rule.variable.VariableProvider;
import com.cl.code.alarm.util.UnmodifiableList;
import com.cl.code.el.param.VariableParam;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 预警策略
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmStrategy<T, V> {

    /**
     * 是否自动更新记录状态
     *
     * @return boolean
     */
    boolean isAutoUpdateRecordStatus();

    /**
     * 获取变量提供者
     *
     * @return {@code Map<String, VariableProvider>}
     */
    Map<VariableParam, VariableProvider> getVariableProviders();

    /**
     * 添加记录
     *
     * @param businessIds 业务标识
     * @param alarmItem   预警项
     * @return {@code Map<Long, RecordSupplement>}
     */
    default Map<Long, AlarmOtherInfo<T>> getOtherInfo(AlarmItem alarmItem, UnmodifiableList<Long> businessIds) {
        return businessIds.stream().collect(Collectors.toMap(Function.identity(), businessId -> getOtherInfo(alarmItem, businessId)));
    }

    /**
     * 添加记录
     *
     * @param businessId 业务标识
     * @param alarmItem  预警项
     * @return {@link AlarmOtherInfo}
     */
    AlarmOtherInfo<T> getOtherInfo(AlarmItem alarmItem, Long businessId);

    /**
     * 获取通知对象
     *
     * @return {@link NotifyTarget}<{@link V}>
     */
    NotifyTargetProvider<V> getNotifyTargetProvider();

    /**
     * 得到通知渠道提供者
     *
     * @return {@code Map<NotifyChannel, NotifyChannelProvider>}
     */
    Map<NotifyChannel, NotifyChannelProvider<T, V>> getNotifyChannelProviders();


    AlarmMessageProvider<T, V> getAlarmMessageProvider();


}
