package com.da.learn;

import com.da.learn.learnboot.maintainpush.excel.MaintainItem;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Test implements Cloneable {
    private int anInt;

    public static void main(String[] args) throws Exception {



        int MAXIMUM_CAPACITY = 1000000;

        int cap = 17;
            int n = cap - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
        System.out.println((n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1);



//        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
//        for (StackTraceElement stackTraceElement : stackTrace) {
//            if ("main".equals(stackTraceElement.getMethodName())) {
//                System.out.println("test");
//            }
//        }
    }


}
