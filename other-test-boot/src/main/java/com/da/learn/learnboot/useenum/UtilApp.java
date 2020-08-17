package com.da.learn.learnboot.useenum;

public class UtilApp {
    public static void main(String[] args) {
        String type = "WEIXIN";
        String decryptMessage = UtilEnum.valueOf(type).decrypt();
        System.out.println(decryptMessage);
    }
}
