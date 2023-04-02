package com.cl.code.alarm.domain.entity;

import com.cl.code.el.expression.base.BooleanExpression;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 预警项
 *
 * @author chengliang
 * @since 1.0.0
 */
@Getter
public class AlarmItem {

    /**
     * 预警项id
     */
    private Long alarmItemId;

    /**
     * 业务范围
     */
    private String businessScope;

    /**
     * 表达式
     */
    private final List<BooleanExpression> expressions = new ArrayList<>();

    /**
     * 预警通知目标
     */
    private final List<String> alarmNoticeTargets = new ArrayList<>();

    /**
     * 通知类型
     */
    private final List<String> alarmNoticeTypes = new ArrayList<>();

}
