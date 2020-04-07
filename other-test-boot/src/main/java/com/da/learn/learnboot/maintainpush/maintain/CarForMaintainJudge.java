package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.MaintainItem;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

import java.util.List;

/**
 * 需根据需求定制
 */
public class CarForMaintainJudge implements MaintainVehicle {

    List<MaintainItem> maintainItemList;

    private Double mileage;

    private Integer dayAfterAak;

    @Override
    public List<MaintainItem> getMaintainItems() {
        return this.maintainItemList;
    }

    @Override
    public Double getMileage() {
        return this.mileage;
    }


    @Override
    public Integer getDayAfterAak() {
        return this.dayAfterAak;
    }
}
