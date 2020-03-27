package com.da.learn.learnboot.maintainpush;

import java.util.List;

public interface MaintainRule extends Repeatable {

    /**
     * 判断是否满足保养规则
     * @param vehicle
     * @return
     */
    List<MaintainResult> maintainJudge(Vehicle vehicle, MaintainItem maintainItem);

}
