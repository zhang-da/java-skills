package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.MaintainStrategy;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

/**
 * 主程序  测试
 */
public class AppTest {

    public static void main(String[] args) {
        //spring中可注入
        MaintainStrategy maintainStrategy = MaintainStrategy.buildMaintainByMileageOnFirstAndRegular(new DefaultFirstByMileageMaintainRule(), new DefaultRegularByMileageMaintainRule());

        MaintainVehicle car = new CarForMaintainJudge();
        //todo 给car赋值

        maintainStrategy.judgeMaintain(car);

    }
}
