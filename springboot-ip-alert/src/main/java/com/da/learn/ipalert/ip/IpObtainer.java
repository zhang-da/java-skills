package com.da.learn.ipalert.ip;

public interface IpObtainer {

    public static final String ERROR_IP = "ERROR_IP";

    String obtainOld();

    String obtainNew();

    void saveIp(String ip);
}
