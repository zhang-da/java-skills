package com.da.learn;

import com.da.learn.learnboot.maintainpush.excel.MaintainItem;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Test implements Cloneable {
    private int anInt;

    public static void main(String[] args) throws Exception {
        String abc = "123456789";

        System.out.println(abc.substring(0, 10));


        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                System.out.println("test");
            }
        }
    }


}
