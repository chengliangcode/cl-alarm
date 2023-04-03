package com.cl.code.alarm.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 监听对象
 *
 * @author chengliang
 * @since 1.0.0
 */
public class MonitorTargets {

    List<ChangeFactor> changeFactors = new ArrayList<>(3);

    public MonitorTargets(List<ChangeFactor> changeFactors) {
        this.changeFactors.addAll(changeFactors);
    }

    public void addChangeFactor(ChangeFactor changeFactor) {
        this.changeFactors.add(changeFactor);
    }

    public List<ChangeFactor> getChangeFactors() {
        return Collections.unmodifiableList(this.changeFactors);
    }

}
