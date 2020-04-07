package com.da.learn.learnboot.maintainpush.maintain.core;

import java.util.ArrayList;
import java.util.List;

public class VehicleMaintainResult {
    private MaintainVehicle vehicle;
    private List<MaintainItemResult> maintainItemResults;

    public VehicleMaintainResult(MaintainVehicle vehicle) {
        this.vehicle = vehicle;
        this.maintainItemResults = new ArrayList<>();
    }

    public List<MaintainItemResult> getMaintainItemResults() {
        return maintainItemResults;
    }

    public MaintainVehicle getVehicle() {
        return vehicle;
    }

    public void addMaintainItemResult(MaintainItem maintainItem, MaintainResult maintainResult) {
        maintainItemResults.add(new MaintainItemResult(maintainItem, maintainResult));
    }

    @Override
    public String toString() {
        return "VehicleMaintainResult{" +
                "vehicle=" + vehicle +
                ", maintainItemResults=" + maintainItemResults +
                '}';
    }
}
