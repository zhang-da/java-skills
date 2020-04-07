package com.da.learn.learnboot.maintainpush.maintain.core;

public class MaintainItemResult {
    private MaintainItem maintainItem;
    private MaintainResult maintainResult;

    public MaintainItemResult(MaintainItem maintainItem, MaintainResult maintainResult) {
        this.maintainItem = maintainItem;
        this.maintainResult = maintainResult;
    }

    public MaintainItem getMaintainItem() {
        return maintainItem;
    }

    public MaintainResult getMaintainResult() {
        return maintainResult;
    }

    @Override
    public String toString() {
        return "MaintainItemResult{" +
                "maintainItem=" + maintainItem +
                ", maintainResult=" + maintainResult +
                '}';
    }
}
