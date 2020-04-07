package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.MaintainItem;
import com.da.learn.learnboot.maintainpush.maintain.core.Range;
import com.da.learn.learnboot.maintainpush.maintain.core.RegularByMileageMaintainRule;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 需根据需求定制
 */
public class DefaultRegularByMileageMaintainRule extends RegularByMileageMaintainRule {

    private static final double BEFORE_REGULAR = 3000D;

    @Override
    public boolean willRepeat() {
        return false;
    }

    @Override
    protected List<Range<Double>> obtainRange(Double currentPoint, Double toNexDistance, int stage) {
        List<Range<Double>> mileageRangeList = new ArrayList<>();
        mileageRangeList.add(new Range<>(stage, 1, currentPoint - BEFORE_REGULAR, toNexDistance));
        mileageRangeList.add(new Range<>(stage, 2, currentPoint, currentPoint + toNexDistance - BEFORE_REGULAR));
        return mileageRangeList;
    }

    @Override
    protected Map<String, String> obtainAdditional(MaintainVehicle vehicle, MaintainItem maintainItem, Range<Double> range) {
        return null;
    }
}
