package com.da.learn.learnboot.maintainpush.maintain.core;

public interface MaintainRule extends Repeatable {

    /**
     * 判断是否满足保养规则
     *
     * @param vehicle
     * @return
     */
    MaintainResult maintainJudge(MaintainVehicle vehicle, MaintainItem maintainItem);

}
