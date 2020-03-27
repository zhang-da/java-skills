package com.da.learn.learnboot.maintainpush;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaintainStrategy {

    private List<MaintainRule> maintainRuleList = new ArrayList<>();

    private MaintainStrategy() {

    }

    /**
     * 首保+定保   里程+时间
     *
     * @return
     */
    public static MaintainStrategy buildMaintainByMileageAndTimeOnFirstAndRegular(FirstByMileageMaintainRule firstByMileageMaintainRule,
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
    public static MaintainStrategy buildMaintainByMileageOnFirstAndRegular(FirstByMileageMaintainRule firstByMileageMaintainRule,
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
    public static MaintainStrategy buildMaintainOnMileageByFirst(FirstByMileageMaintainRule firstByMileageMaintainRule) {
        MaintainStrategy maintainStrategy = new MaintainStrategy();
        maintainStrategy.addRule(firstByMileageMaintainRule);
        return maintainStrategy;
    }

    /**
     * 执行保养规则判断
     *
     * @param vehicle
     */
    public void judgeMaintain(Vehicle vehicle) {
        if (vehicle == null) {
            return;
        }
        if (ObjectUtils.isEmpty(vehicle.getMaintainItems())) {
            return;
        }

        doJudgeMaintain(vehicle);
    }

    private void doJudgeMaintain(Vehicle vehicle) {
        vehicle.getMaintainItems().forEach(maintainItem -> {
            List<MaintainResult> maintainResultOneItem = new ArrayList<>();
            for (MaintainRule maintainRule : maintainRuleList) {
                List<MaintainResult> maintainResult = maintainRule.maintainJudge(vehicle, maintainItem);
                if (!ObjectUtils.isEmpty(maintainResult)) {
                    maintainResultOneItem.addAll(maintainResult);
                }
            }
            MaintainItemResult maintainItemResult = handleOneItemMaintainResult(maintainResultOneItem);
        });
    }

    private MaintainItemResult handleOneItemMaintainResult(List<MaintainResult> maintainResultOneItem) {
        if (ObjectUtils.isEmpty(maintainResultOneItem)) {
            return null;
        }
        maintainResultOneItem.sort(Comparator.comparing().thenComparing());
//        maintainResultOneItem.stream().sorted()
        return null;
    }


    private MaintainStrategy addRule(MaintainRule maintainRule) {
        this.maintainRuleList.add(maintainRule);
        return this;
    }

}
