package com.cl.code.alarm.domian.rule;

import com.cl.code.el.expression.base.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * 报警规则
 *
 * @author chengliang
 * @since 1.0.0
 */
@EqualsAndHashCode
@AllArgsConstructor
public class AlarmRules {

    private final List<BooleanExpression> ruleList;

    public void addRule(BooleanExpression rule) {
        if (contains(rule)) {
            return;
        }
        this.ruleList.add(rule);
    }

    public List<BooleanExpression> getRuleList() {
        return Collections.unmodifiableList(ruleList);
    }

    public boolean contains(BooleanExpression expression) {
        return ruleList.stream().anyMatch(item -> item.getExpression().equals(expression.getExpression()));
    }

}
