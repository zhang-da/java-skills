package com.da.learn.learnboot.maintainpush.maintain.core;

import java.util.List;

public abstract class FirstByTimeMaintainRule extends AbstractMaintainRule<Integer> {

    protected static final int STAGE = 0;

    @Override
    public MaintainResult maintainJudge(MaintainVehicle vehicle, MaintainItem maintainItem) {
        Integer dayAfterAak = vehicle.getDayAfterAak();
        if (dayAfterAak == null || dayAfterAak <= 0) {
            return null;
        }
        List<Range<Integer>> dayRangeList = obtainRange(maintainItem.getFirstDay(), maintainItem.getRegularDay(), STAGE);
        Integer lastDay = maintainItem.getLastDay();
        for (Range<Integer> dayRange : dayRangeList) {
            switch (judgeMethod(dayAfterAak, lastDay, dayRange)) {
                case KEEP:
                    return MaintainResult.buildKeepFirstDayResult(dayRange.getStage(), dayRange.getSection(), obtainAdditional(vehicle, maintainItem, dayRange));
                case PASS:
                    break;
                case PASS_BY_REPEAT:
                    return MaintainResult.buildKeepRepeatFirstDayResult(dayRange.getStage(), dayRange.getSection(), obtainAdditional(vehicle, maintainItem, dayRange));
            }
        }
        return null;
    }
}
