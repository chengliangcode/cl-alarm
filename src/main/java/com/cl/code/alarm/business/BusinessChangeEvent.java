package com.cl.code.alarm.business;

import com.cl.code.alarm.monitor.Factor;
import com.cl.code.alarm.util.UnmodifiableList;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * 业务变更事件
 *
 * @author chengliang
 * @since 1.0.0
 */
@Getter
public class BusinessChangeEvent {

    private final Factor factor;

    private final UnmodifiableList<Long> businessIds;

    public BusinessChangeEvent(Factor factor, Long businessId) {
        this(factor, Lists.newArrayList(businessId));
    }

    public BusinessChangeEvent(Factor factor, List<Long> businessIds) {
        this.factor = factor;
        this.businessIds = new UnmodifiableList<>(businessIds);
    }

}
