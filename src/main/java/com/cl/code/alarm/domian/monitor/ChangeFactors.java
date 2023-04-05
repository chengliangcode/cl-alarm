package com.cl.code.alarm.domian.monitor;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * 变动因素
 *
 * @author chengliang
 * @since 1.0.0
 */
@EqualsAndHashCode
@AllArgsConstructor
public class ChangeFactors {

    private final List<Factor> factors;

    public void addFactor(Factor factor) {
        if (contains(factor)) {
            return;
        }
        this.factors.add(factor);
    }

    public List<Factor> getFactors() {
        return Collections.unmodifiableList(this.factors);
    }

    public boolean contains(Factor factor) {
        return factors.stream().anyMatch(item -> item.getName().equals(factor.getName()));
    }

}
