package com.da.learn.flink.entity;

import java.util.Map;

public class UserEvent {

    /**
     * 事件发生时间戳
     */
    private Long timestamp;
    /**
     * 用户标记
     */
    private String userId;
    /**
     * app标记
     */
    private String appName;
    /**
     * 页面标记（可空）
     */
    private String pageName;
    /**
     * 页面内项目（如：按钮，可空）
     */
    private String item;
    /**
     * 事件（如：click、open、close等）
     */
    private String event;

    /**
     * 请求ip地址 （网关获取）
     */
    private String ip;

    /**
     * 来源信息(如：web，android，ios，h5，小程序等)
     */
    private String source;
    /**
     * 附加参数
     */
    private Map<String, String> params;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                ", appName='" + appName + '\'' +
                ", pageName='" + pageName + '\'' +
                ", item='" + item + '\'' +
                ", event='" + event + '\'' +
                ", ip='" + ip + '\'' +
                ", source='" + source + '\'' +
                ", params=" + params +
                '}';
    }
}
