package com.da.learn;

import com.da.learn.learnboot.maintainpush.excel.MaintainItem;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import javax.jms.Connection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Test implements Cloneable {
    private int anInt;

    public static void main(String[] args) throws Exception {
        System.out.println(Instant.now().toEpochMilli());

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("url");
        Connection conn = factory.createConnection("abc", "abc");
        conn.createSession();


//        new Thread(() -> {
//           System.out.println("hello");
//       }).start();



//        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
//        for (StackTraceElement stackTraceElement : stackTrace) {
//            if ("main".equals(stackTraceElement.getMethodName())) {
//                System.out.println("test");
//            }
//        }
    }


}
