package com.cl.code.alarm.business;

import com.cl.code.alarm.monitor.Factor;
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

    private final List<Long> businessIds;

    public BusinessChangeEvent(Factor factor, List<Long> businessIds) {
        this.factor = factor;
        this.businessIds = businessIds;
    }


}
