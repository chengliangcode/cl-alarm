package com.cl.code.alarm.monitor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 变动因素
 *
 * @author chengliang
 * @since 1.0.0
 */
public class ChangeFactors {

    List<Factor> factors = new ArrayList<>(3);

    public ChangeFactors(List<Factor> factors) {
        this.factors.addAll(factors);
    }

    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }

    public List<Factor> getFactors() {
        return Collections.unmodifiableList(this.factors);
    }

}
