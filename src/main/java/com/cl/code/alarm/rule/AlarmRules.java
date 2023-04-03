package com.cl.code.alarm.rule;

import com.cl.code.el.expression.base.BooleanExpression;

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

    private final List<BooleanExpression> ruleList = new ArrayList<>();

    public AlarmRules(BooleanExpression rule) {
        this.ruleList.add(rule);
    }

    public AlarmRules(List<BooleanExpression> ruleList) {
        this.ruleList.addAll(ruleList);
    }

    public void addRule(BooleanExpression rule) {
        this.ruleList.add(rule);
    }

    public List<BooleanExpression> getRuleList() {
        return Collections.unmodifiableList(ruleList);
    }

}
