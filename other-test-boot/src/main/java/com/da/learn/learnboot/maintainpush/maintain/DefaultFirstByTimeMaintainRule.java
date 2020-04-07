package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.FirstByTimeMaintainRule;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainItem;
import com.da.learn.learnboot.maintainpush.maintain.core.Range;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 需根据需求定制
 */
public class DefaultFirstByTimeMaintainRule extends FirstByTimeMaintainRule {

    private static final int BEFORE_FIRST = 15;
    private static final int BEFORE_REGULAR = 15;

    @Override
    public boolean willRepeat() {
        return false;
    }

    @Override
    protected List<Range<Integer>> obtainRange(Integer first, Integer regular, int stage) {
        List<Range<Integer>> dayRangeList = new ArrayList<>();
        dayRangeList.add(new Range<>(stage, 1, first - BEFORE_FIRST, first));

        if (regular != null) {
            dayRangeList.add(new Range<>(stage, 2, first, first + regular - BEFORE_REGULAR));
        }
        return dayRangeList;
    }

    @Override
    protected Map<String, String> obtainAdditional(MaintainVehicle vehicle, MaintainItem maintainItem, Range<Integer> range) {
        return null;
    }
}
