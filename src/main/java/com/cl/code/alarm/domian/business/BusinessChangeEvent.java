package com.cl.code.alarm.domian.business;

import com.cl.code.alarm.domian.monitor.Factor;
import com.cl.code.alarm.util.CollectionUtils;
import com.cl.code.alarm.util.UnmodifiableList;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务变更事件
 *
 * @author chengliang
 * @since 1.0.0
 */
@Getter
public class BusinessChangeEvent {

    private final List<Factor> factors;

    private final UnmodifiableList<Long> businessIds;

    public BusinessChangeEvent(Long businessId, Factor... factors) {
        this(Lists.newArrayList(businessId), factors);
    }

    public BusinessChangeEvent(List<Long> businessIds, Factor... factors) {
        if (factors == null || factors.length == 0) {
            throw new IllegalArgumentException("变更因素不能为空");
        }
        if (CollectionUtils.isNullOrEmpty(businessIds)) {
            throw new IllegalArgumentException("业务id不能为空");
        }
        this.factors = Arrays.stream(factors).collect(Collectors.toList());
        this.businessIds = new UnmodifiableList<>(businessIds);
    }

}
