package com.da.learn.learnboot.maintainpush;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FirstByMileageMaintainRule extends AbstractMaintainRule<Double> {

    protected static final int STAGE = 0;

    @Override
    public List<MaintainResult> maintainJudge(Vehicle vehicle, MaintainItem maintainItem) {
        List<MaintainResult> maintainResultList = new ArrayList<>();
        Double mileage = vehicle.getMileage();
        if (mileage == null) {
            // 返回不存在
            return maintainResultList;
        }
        List<MileageRange> mileageRangeList = obtainRange(maintainItem.getFirstMileage(), maintainItem.getRegularMileage());
        if (ObjectUtils.isEmpty(mileageRangeList)) {
            return maintainResultList;
        }
        Double lastMileage = maintainItem.getLastMileage();
        for (MileageRange mileageRange : mileageRangeList) {
            switch (judgeMethod(mileage, lastMileage, mileageRange)) {
                case KEEP:
                    maintainResultList.add(MaintainResult.buildKeepFirstMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange)));
                    break;
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    maintainResultList.add(MaintainResult.buildKeepRepeatFirstMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange)));
                    break;
            }
        }
        return maintainResultList;
    }

    /**
     * 获取需要保养提醒的范围
     *
     * @param firstMileage 首保里程
     * @return
     */
    protected abstract List<MileageRange> obtainRange(Double firstMileage, Double regularMileage);

    protected abstract Map<String, String> obtainAdditional(Vehicle vehicle, MaintainItem maintainItem, MileageRange mileageRange);


}
