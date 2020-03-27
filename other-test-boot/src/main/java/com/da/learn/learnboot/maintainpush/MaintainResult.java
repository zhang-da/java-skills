package com.da.learn.learnboot.maintainpush;

import java.util.Map;

public class MaintainResult {

    private static final int RESULT_TYPE_FIRST_MILEAGE = 3;
    private static final int RESULT_TYPE_FIRST_TIME = 4;
    private static final int RESULT_TYPE_REGULAR_MILEAGE = 1;
    private static final int RESULT_TYPE_REGULAR_TIME = 2;

    /**
     * 0 无保养信息(没用了)
     * 1 有保养信息
     * 2 有保养信息，但有重复记录
     */
    private Integer flag;

    private Integer stage;

    private Integer section;

    private Integer type;

    private Map<String, String> additional;

    private void setFlag(Integer flag) {
        this.flag = flag;
    }

    private void setStage(Integer stage) {
        this.stage = stage;
    }

    private void setSection(Integer section) {
        this.section = section;
    }

    private void setType(Integer type) {
        this.type = type;
    }

    private void setAdditional(Map<String, String> additional) {
        this.additional = additional;
    }

    public Integer getStage() {
        return stage;
    }

    public Integer getSection() {
        return section;
    }

    public Integer getType() {
        return type;
    }

    public Map<String, String> getAdditional() {
        return additional;
    }

    private MaintainResult() {
    }


    public static MaintainResult buildKeepFirstMileageResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(1, stage, section, RESULT_TYPE_FIRST_MILEAGE, additional);
    }

    public static MaintainResult buildKeepRepeatFirstMileageResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(2, stage, section, RESULT_TYPE_FIRST_MILEAGE, additional);
    }

    private static MaintainResult buildMaintainResult(Integer flag, Integer stage, Integer section, Integer type, Map<String, String> additional) {
        MaintainResult maintainResult = new MaintainResult();
        maintainResult.setFlag(flag);
        maintainResult.setStage(stage);
        maintainResult.setSection(section);
        maintainResult.setType(type);
        maintainResult.setAdditional(additional);
        return maintainResult;
    }

}
