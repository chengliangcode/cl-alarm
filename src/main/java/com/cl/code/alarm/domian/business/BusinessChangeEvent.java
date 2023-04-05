package com.cl.code.alarm.domian.business;

import com.cl.code.alarm.domian.monitor.Factor;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.UnmodifiableList;
import com.google.common.base.Strings;
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
        if (factor == null || Strings.isNullOrEmpty(factor.getName())) {
            throw new IllegalArgumentException("变更因素不能为空");
        }
        if (CollectionUtils.isNullOrEmpty(businessIds)) {
            throw new IllegalArgumentException("业务id不能为空");
        }
        this.factor = factor;
        this.businessIds = new UnmodifiableList<>(businessIds);
    }


}
