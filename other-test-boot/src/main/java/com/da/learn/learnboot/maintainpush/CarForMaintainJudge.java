package com.da.learn.learnboot.maintainpush;

import java.util.List;

/**
 * 需根据需求定制
 */
public class CarForMaintainJudge implements Vehicle {

    List<MaintainItem> maintainItemList;

    private Double mileage;

    @Override
    public List<MaintainItem> getMaintainItems() {
        return this.maintainItemList;
    }

    @Override
    public Double getMileage() {
        return this.mileage;
    }
}
