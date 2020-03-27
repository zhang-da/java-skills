package com.da.learn.learnboot.maintainpush;

import java.util.List;

public interface Vehicle {

    /**
     * 获取车辆保养项目及保养规则
     * @return
     */
    List<MaintainItem> getMaintainItems();

    /**
     * 获取车辆实时里程
     * @return
     */
    Double getMileage();
}
