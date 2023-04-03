package com.cl.code.alarm.rule;

import com.cl.code.el.expression.base.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 报警规则
 *
 * @author chengliang
 * @since 1.0.0
 */
public class AlarmRules {

    private final List<Expression<Boolean>> ruleList = new ArrayList<>();

    public AlarmRules(Expression<Boolean> rule) {
        this.ruleList.add(rule);
    }

    public AlarmRules(List<Expression<Boolean>> ruleList) {
        this.ruleList.addAll(ruleList);
    }

    public void addRule(Expression<Boolean> rule) {
        this.ruleList.add(rule);
    }

    public List<Expression<Boolean>> getRuleList() {
        return Collections.unmodifiableList(ruleList);
    }

}
