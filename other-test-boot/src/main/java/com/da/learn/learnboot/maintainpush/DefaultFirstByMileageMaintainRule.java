package com.da.learn.learnboot.maintainpush;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<MileageRange> obtainRange(Double firstMileage, Double regularMileage) {
        List<MileageRange> mileageRangeList = new ArrayList<>();
        if (firstMileage.compareTo(10000.0D) < 0) {
            mileageRangeList.add(new MileageRange(STAGE, 1, firstMileage - BEFORE_FIRST_WHEN_LESS_THAN_1W, firstMileage));
        } else {
            mileageRangeList.add(new MileageRange(STAGE, 1, firstMileage - BEFORE_FIRST_WHEN_MORE_THAN_1W, firstMileage));
        }

        if (regularMileage != null) {
            mileageRangeList.add(new MileageRange(STAGE, 2, firstMileage, regularMileage - BEFORE_REGULAR));
        }


        return mileageRangeList;
    }

    @Override
    protected Map<String, String> obtainAdditional(Vehicle vehicle, MaintainItem maintainItem, MileageRange mileageRange) {
        return new HashMap<>();
    }

    @Override
    public boolean willRepeat() {
        return false;
    }
}
