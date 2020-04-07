package com.da.learn.learnboot.maintainpush.maintain.core;

import java.util.Map;

public class MaintainResult {

    /**
     * type 首保公里
     */
    private static final int RESULT_TYPE_FIRST_MILEAGE = 3;
    /**
     * type 首保时间
     */
    private static final int RESULT_TYPE_FIRST_TIME = 4;
    /**
     * type 定保公里
     */
    private static final int RESULT_TYPE_REGULAR_MILEAGE = 1;
    /**
     * type 定保时间
     */
    private static final int RESULT_TYPE_REGULAR_TIME = 2;

    /**
     * 0 无保养信息(没用了)
     * 1 有保养信息
     * 2 有保养信息，但有重复记录
     */
    private Integer flag;

    /**
     * 大段
     * 0：首保
     * 1：第一次定保
     * 2：第二次定保
     * 。。。。
     */
    private Integer stage;

    /**
     * 小段（时间先后顺序）
     * 1：第一小段
     * 2：第二小段
     */
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

    public boolean hasMaintain() {
        return this.flag == 1;
    }

    public boolean isFirstMaintain() {
        return this.stage == 0;
    }

    public boolean isRegularMaintain() {
        return this.stage > 0;
    }


    public static MaintainResult buildKeepFirstMileageResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(1, stage, section, RESULT_TYPE_FIRST_MILEAGE, additional);
    }

    public static MaintainResult buildKeepRepeatFirstMileageResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(2, stage, section, RESULT_TYPE_FIRST_MILEAGE, additional);
    }

    public static MaintainResult buildKeepFirstDayResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(1, stage, section, RESULT_TYPE_FIRST_TIME, additional);
    }

    public static MaintainResult buildKeepRepeatFirstDayResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(2, stage, section, RESULT_TYPE_FIRST_TIME, additional);
    }

    public static MaintainResult buildKeepRegularMileageResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(1, stage, section, RESULT_TYPE_REGULAR_MILEAGE, additional);
    }

    public static MaintainResult buildKeepRepeatRegularMileageResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(2, stage, section, RESULT_TYPE_REGULAR_MILEAGE, additional);
    }

    public static MaintainResult buildKeepRegularDayResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(1, stage, section, RESULT_TYPE_REGULAR_TIME, additional);
    }

    public static MaintainResult buildKeepRepeatRegularDayResult(Integer stage, Integer section, Map<String, String> additional) {
        return buildMaintainResult(2, stage, section, RESULT_TYPE_REGULAR_TIME, additional);
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

    @Override
    public String toString() {
        return "MaintainResult{" +
                "flag=" + flag +
                ", stage=" + stage +
                ", section=" + section +
                ", type=" + type +
                ", additional=" + additional +
                '}';
    }
}
