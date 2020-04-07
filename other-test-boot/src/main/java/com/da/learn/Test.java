package com.da.learn;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class Test implements Cloneable {
    private int anInt;

    public static void main(String[] args) throws Exception {
        Set<String> setabc = new HashSet<>();
        setabc.add("test");
        setabc.add("哈哈");
        setabc.add("test");
        setabc.add("哈哈2");
        System.out.println(StringUtils.join(setabc, ","));

        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                System.out.println("test");
            }
        }
    }


}
