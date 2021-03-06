package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.MaintainItem;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

import java.util.ArrayList;
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

    public CarForMaintainJudge() {
        this.maintainItemList = new ArrayList<>();
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public void setDayAfterAak(Integer dayAfterAak) {
        this.dayAfterAak = dayAfterAak;
    }

    public void addItem(MaintainItem item) {
        this.maintainItemList.add(item);
    }
}
