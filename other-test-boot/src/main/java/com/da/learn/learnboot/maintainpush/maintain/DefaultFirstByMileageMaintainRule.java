package com.da.learn.learnboot.maintainpush.maintain;

import com.da.learn.learnboot.maintainpush.maintain.core.FirstByMileageMaintainRule;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainItem;
import com.da.learn.learnboot.maintainpush.maintain.core.Range;
import com.da.learn.learnboot.maintainpush.maintain.core.MaintainVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 需根据需求定制
 */
public class DefaultFirstByMileageMaintainRule extends FirstByMileageMaintainRule {

    private static final double BEFORE_FIRST_WHEN_LESS_THAN_1W = 1000D;
    private static final double BEFORE_FIRST_WHEN_MORE_THAN_1W = 3000D;
    private static final double BEFORE_REGULAR = 3000D;


    @Override
    public List<Range<Double>> obtainRange(Double firstMileage, Double regularMileage, int stage) {
        List<Range<Double>> mileageRangeList = new ArrayList<>();
        if (firstMileage.compareTo(10000.0D) < 0) {
            mileageRangeList.add(new Range<>(stage, 1, firstMileage - BEFORE_FIRST_WHEN_LESS_THAN_1W, firstMileage));
        } else {
            mileageRangeList.add(new Range<>(stage, 1, firstMileage - BEFORE_FIRST_WHEN_MORE_THAN_1W, firstMileage));
        }

        if (regularMileage != null) {
            mileageRangeList.add(new Range<>(stage, 2, firstMileage, firstMileage + regularMileage - BEFORE_REGULAR));
        }


        return mileageRangeList;
    }

    @Override
    protected Map<String, String> obtainAdditional(MaintainVehicle vehicle, MaintainItem maintainItem, Range<Double> mileageRange) {
        return null;
    }

    @Override
    public boolean willRepeat() {
        return false;
    }
}
