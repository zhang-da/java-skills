package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.MaintainItem;
import com.da.learn.learnboot.maintainpush.maintain.core.Range;
import com.da.learn.learnboot.maintainpush.maintain.core.RegularByTimeMaintainRule;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 需根据需求定制
 */
public class DefaultRegularTimeMaintainRule extends RegularByTimeMaintainRule {

    private static final int BEFORE_REGULAR = 15;

    @Override
    public boolean willRepeat() {
        return false;
    }

    @Override
    protected List<Range<Integer>> obtainRange(Integer currentPoint, Integer toNexDistance, int stage) {
        List<Range<Integer>> dayRangeList = new ArrayList<>();
        dayRangeList.add(new Range<>(stage, 1, currentPoint - BEFORE_REGULAR, currentPoint));
        dayRangeList.add(new Range<>(stage, 2, currentPoint, currentPoint + toNexDistance - BEFORE_REGULAR));
        return dayRangeList;
    }

    @Override
    protected Map<String, String> obtainAdditional(MaintainVehicle vehicle, MaintainItem maintainItem, Range<Integer> range) {
        return null;
    }
}
