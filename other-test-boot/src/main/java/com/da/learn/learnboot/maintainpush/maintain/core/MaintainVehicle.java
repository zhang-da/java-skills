package com.da.learn.learnboot.maintainpush.maintain.core;

import java.util.List;

public interface MaintainVehicle {

    /**
     * 获取车辆保养项目及保养规则
     *
     * @return
     */
    List<MaintainItem> getMaintainItems();

    /**
     * 获取车辆实时里程
     *
     * @return
     */
    Double getMileage();

    Integer getDayAfterAak();

    String toString();
}
