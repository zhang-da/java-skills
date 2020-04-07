package com.da.learn.learnboot.maintainpush.maintain.core;

import org.springframework.util.ObjectUtils;

import java.util.List;

public abstract class RegularByMileageMaintainRule extends AbstractMaintainRule<Double> {

    private static final int STAGE_BEGIN = 1;

    @Override
    public MaintainResult maintainJudge(MaintainVehicle vehicle, MaintainItem maintainItem) {
        Double mileage = vehicle.getMileage();
        if (mileage == null) {
            // 返回不存在
            return null;
        }
        Double regularMileage = maintainItem.getRegularMileage();
        if (regularMileage == null) {
            return null;
        }
        Double lastMileage = maintainItem.getLastMileage();
        int stage = STAGE_BEGIN;
        double currentPoint = maintainItem.getFirstMileage() + regularMileage;
        double nextPoint = currentPoint + regularMileage;
        while (mileage.compareTo(nextPoint) >= 0) {
            currentPoint = nextPoint;
            nextPoint = currentPoint + regularMileage;
            stage = stage + 1;
        }
        //看一下current
        List<Range<Double>> rangeList = obtainRange(currentPoint, regularMileage, stage);
        if (ObjectUtils.isEmpty(rangeList)) {
            //error
            return null;
        }
        for (Range<Double> mileageRange : rangeList) {
            switch (judgeMethod(mileage, lastMileage, mileageRange)) {
                case KEEP:
                    return MaintainResult.buildKeepRegularMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange));
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    return MaintainResult.buildKeepRepeatRegularMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange));
            }
        }

        //若还未返回，再看一下next
        currentPoint = nextPoint;
        stage = stage + 1;
        List<Range<Double>> rangeListNext = obtainRange(currentPoint, regularMileage, stage);
        if (ObjectUtils.isEmpty(rangeListNext)) {
            //error
            return null;
        }
        for (Range<Double> mileageRange : rangeListNext) {
            switch (judgeMethod(mileage, lastMileage, mileageRange)) {
                case KEEP:
                    return MaintainResult.buildKeepRegularMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange));
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    return MaintainResult.buildKeepRepeatRegularMileageResult(mileageRange.getStage(), mileageRange.getSection(), obtainAdditional(vehicle, maintainItem, mileageRange));
            }
        }

        return null;
    }


}
