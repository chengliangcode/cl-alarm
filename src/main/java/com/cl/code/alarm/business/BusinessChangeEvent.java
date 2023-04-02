package com.cl.code.alarm.business;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;

/**
 * 业务变更事件
 *
 * @author chengliang
 * @since 1.0.0
 */
@Getter
public class BusinessChangeEvent {

    private final BusinessScope businessScope;

    private final String businessIndex;

    private BusinessChangeEvent(BusinessScope businessScope, String businessIndex) {
        this.businessScope = businessScope;
        this.businessIndex = businessIndex;
    }

    public static BusinessChangeEvent of(BusinessScope businessScope, Object businessIndex) {
        return new BusinessChangeEvent(businessScope, businessIndex == null ? null : JSON.toJSONString(businessIndex));
    }


}
