package com.da.learn.learnboot.maintainpush.maintain.core;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * created by zhang da
 * mail:zhangda@aerozhonghuan.com
 * mail:flyingzd@foxmail.com
 */
public class MaintainStrategy {

    private List<MaintainRule> maintainRuleList = new ArrayList<>();

    private MaintainStrategy() {

    }

    /**
     * 首保+定保   里程+时间
     *
     * @return
     */
    public static MaintainStrategy buildMaintainByMileageAndTimeOnFirstAndRegular(
            FirstByMileageMaintainRule firstByMileageMaintainRule,
            FirstByTimeMaintainRule firstByTimeMaintainRule,
            RegularByMileageMaintainRule regularByMileageMaintainRule,
            RegularByTimeMaintainRule regularByTimeMaintainRule) {
        MaintainStrategy maintainStrategy = new MaintainStrategy();
        maintainStrategy.addRule(firstByMileageMaintainRule)
                .addRule(firstByTimeMaintainRule)
                .addRule(regularByMileageMaintainRule)
                .addRule(regularByTimeMaintainRule);
        return maintainStrategy;
    }

    /**
     * 首保+定保   里程
     *
     * @return
     */
    public static MaintainStrategy buildMaintainByMileageOnFirstAndRegular(
            FirstByMileageMaintainRule firstByMileageMaintainRule,
            RegularByMileageMaintainRule regularByMileageMaintainRule) {
        MaintainStrategy maintainStrategy = new MaintainStrategy();
        maintainStrategy.addRule(firstByMileageMaintainRule)
                .addRule(regularByMileageMaintainRule);
        return maintainStrategy;
    }

    /**
     * 首保里程
     *
     * @return
     */
    public static MaintainStrategy buildMaintainByMileageOnFirst(
            FirstByMileageMaintainRule firstByMileageMaintainRule) {
        MaintainStrategy maintainStrategy = new MaintainStrategy();
        maintainStrategy.addRule(firstByMileageMaintainRule);
        return maintainStrategy;
    }

    /**
     * 执行保养规则判断
     *
     * @param vehicle
     */
    public VehicleMaintainResult judgeMaintain(MaintainVehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        if (ObjectUtils.isEmpty(vehicle.getMaintainItems())) {
            return null;
        }

        return doJudgeMaintain(vehicle);
    }

    private VehicleMaintainResult doJudgeMaintain(MaintainVehicle vehicle) {
        VehicleMaintainResult vehicleMaintainResult = new VehicleMaintainResult(vehicle);
        vehicle.getMaintainItems().forEach(maintainItem -> {
            List<MaintainResult> maintainResultOneItem = new ArrayList<>();
            for (MaintainRule maintainRule : maintainRuleList) {
                MaintainResult maintainResult = maintainRule.maintainJudge(vehicle, maintainItem);
                if (maintainResult != null) {
                    maintainResultOneItem.add(maintainResult);
                }
            }
            MaintainResult maintainResult = handleOneItemMaintainResult(maintainResultOneItem);
            if (maintainResult != null) {
                vehicleMaintainResult.addMaintainItemResult(maintainItem, maintainResult);
            }
        });
        if (ObjectUtils.isEmpty(vehicleMaintainResult.getMaintainItemResults())) {
            return null;
        }
        return vehicleMaintainResult;
    }

    private MaintainResult handleOneItemMaintainResult(List<MaintainResult> maintainResultOneItem) {
        if (ObjectUtils.isEmpty(maintainResultOneItem)) {
            return null;
        }
        if (maintainResultOneItem.size() == 1) {
            MaintainResult maintainResult = maintainResultOneItem.get(0);
            return maintainResult.hasMaintain() ? maintainResult : null;
        }
        Comparator<MaintainResult> byStageDesc = Comparator.comparing(MaintainResult::getStage).reversed();
        Comparator<MaintainResult> bySectionDesc = Comparator.comparing(MaintainResult::getSection).reversed();
        Comparator<MaintainResult> byType = Comparator.comparing(MaintainResult::getType);
        maintainResultOneItem.sort(byStageDesc.thenComparing(bySectionDesc).thenComparing(byType));
        if (maintainResultOneItem.get(0).hasMaintain()) {
            return maintainResultOneItem.get(0);
        }
        return null;
    }


    private MaintainStrategy addRule(MaintainRule maintainRule) {
        this.maintainRuleList.add(maintainRule);
        return this;
    }

}
