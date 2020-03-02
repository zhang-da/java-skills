package com.da.learn.ipalert.alerter;

import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Alerter {
    private Set<Alert> alertSet;

    public Alerter(Alert... alerts) {
        this.alertSet = new HashSet<>();
        Collections.addAll(alertSet, alerts);
    }

    public void alert(String ip) {
        if (ObjectUtils.isEmpty(this.alertSet)) {
            return;
        }
        alertSet.forEach(alert -> alert.execute(ip));
    }

    public void addAlert(Alert... alerts) {
        if (alerts.length == 0) {
            return;
        }
        Collections.addAll(alertSet, alerts);
    }
}
