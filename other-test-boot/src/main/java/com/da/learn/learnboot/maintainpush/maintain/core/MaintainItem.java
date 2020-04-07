package com.da.learn.learnboot.maintainpush.maintain.core;

public interface MaintainItem {

    /**
     * 首保里程
     *
     * @return
     */
    Double getFirstMileage();

    /**
     * 定保里程
     *
     * @return
     */
    Double getRegularMileage();

    /**
     * 上次保养里程
     *
     * @return
     */
    Double getLastMileage();

    /**
     * 首保aak后天数
     *
     * @return
     */
    Integer getFirstDay();

    /**
     * 定保时间(天)
     *
     * @return
     */
    Integer getRegularDay();

    /**
     * 上次保养aak后天数
     *
     * @return
     */
    Integer getLastDay();

    String toString();

}
