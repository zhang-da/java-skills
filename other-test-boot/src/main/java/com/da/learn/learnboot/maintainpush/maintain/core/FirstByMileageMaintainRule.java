package com.da.learn.learnboot.maintainpush.maintain.core;

import org.springframework.util.ObjectUtils;

import java.util.List;

public abstract class FirstByMileageMaintainRule extends AbstractMaintainRule<Double> {

    protected static final int STAGE = 0;

    @Override
    public MaintainResult maintainJudge(MaintainVehicle vehicle, MaintainItem maintainItem) {
        Double mileage = vehicle.getMileage();
        if (mileage == null) {
            // 返回不存在
            return null;
        }
        List<Range<Double>> mileageRangeList = obtainRange(maintainItem.getFirstMileage(), maintainItem.getRegularMileage(), STAGE);
        if (ObjectUtils.isEmpty(mileageRangeList)) {
            return null;
        }
        Double lastMileage = maintainItem.getLastMileage();
        for (Range<Double> mileageRange : mileageRangeList) {
            switch (judgeMethod(mileage, lastMileage, mileageRange)) {
                case KEEP:
                    return MaintainResult.buildKeepFirstMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange));
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    return MaintainResult.buildKeepRepeatFirstMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange));
            }
        }
        return null;
    }

}
