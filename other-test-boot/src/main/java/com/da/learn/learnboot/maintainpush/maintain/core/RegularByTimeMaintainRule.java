package com.da.learn.learnboot.maintainpush.maintain.core;

import org.springframework.util.ObjectUtils;

import java.util.List;

public abstract class RegularByTimeMaintainRule extends AbstractMaintainRule<Integer> {

    private static final int STAGE_BEGIN = 1;

    @Override
    public MaintainResult maintainJudge(MaintainVehicle vehicle, MaintainItem maintainItem) {
        Integer dayAfterAak = vehicle.getDayAfterAak();
        if (dayAfterAak == null || dayAfterAak <= 0) {
            return null;
        }
        Integer regularDay = maintainItem.getRegularDay();
        if (regularDay == null) {
            return null;
        }
        Integer lastDay = maintainItem.getLastDay();
        int stage = STAGE_BEGIN;
        int currentPoint = maintainItem.getFirstDay() + regularDay;
        int nextPoint = currentPoint + regularDay;
        while (dayAfterAak.compareTo(nextPoint) >= 0) {
            currentPoint = nextPoint;
            nextPoint = currentPoint + regularDay;
            stage = stage + 1;
        }

        //看一下current
        List<Range<Integer>> rangeList = obtainRange(currentPoint, regularDay, stage);
        if (ObjectUtils.isEmpty(rangeList)) {
            //error
            return null;
        }
        for (Range<Integer> dayRange : rangeList) {
            switch (judgeMethod(dayAfterAak, lastDay, dayRange)) {
                case KEEP:
                    return MaintainResult.buildKeepRegularDayResult(dayRange.getStage(), dayRange.getSection(), obtainAdditional(vehicle, maintainItem, dayRange));
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    return MaintainResult.buildKeepRepeatRegularDayResult(dayRange.getStage(), dayRange.getSection(), obtainAdditional(vehicle, maintainItem, dayRange));
            }
        }
        //若还未返回，再看一下next
        currentPoint = nextPoint;
        stage = stage + 1;
        List<Range<Integer>> rangeListNext = obtainRange(currentPoint, regularDay, stage);
        if (ObjectUtils.isEmpty(rangeListNext)) {
            //error
            return null;
        }
        for (Range<Integer> dayRange : rangeListNext) {
            switch (judgeMethod(dayAfterAak, lastDay, dayRange)) {
                case KEEP:
                    return MaintainResult.buildKeepRegularDayResult(dayRange.getStage(), dayRange.getSection(), obtainAdditional(vehicle, maintainItem, dayRange));
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    return MaintainResult.buildKeepRepeatRegularDayResult(dayRange.getStage(), dayRange.getSection(), obtainAdditional(vehicle, maintainItem, dayRange));
            }
        }

        return null;
    }
}
