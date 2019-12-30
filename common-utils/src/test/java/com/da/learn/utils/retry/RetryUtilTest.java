package com.da.learn.utils.retry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RetryUtilTest {

    @Test
    public void retry1() {
        List<String> retry1 = RetryUtil.retry1(5, RetryUtilTest::foo1, (list, e) -> e != null || list == null || list.isEmpty());
    }

    @Test
    public void retry2() {
        RetryUtil.retry1(5, RetryUtilTest::foo2, (s, e) -> e != null || s == null);
    }

    @Test
    public void retry3() {
        List<String> retry = RetryUtil.retry1(5, () -> {
            try {
                return foo3();
            } catch (Exception e) {
                return null;

            }
        }, (list, e) -> {
            return e != null || list == null || list.isEmpty();
        });
    }

    @Test
    public void retry4() throws Exception {
        List<String> retry1 = RetryUtil.retry2(5, RetryUtilTest::foo3, (list, e) -> e != null || list == null || list.isEmpty());
    }

    private static List<String> foo1() {// 没有显示抛出异常
        System.out.println("调用方法");
        // 模拟抛出异常
        System.out.println(1/0);
        List<String> list = new ArrayList<>();
        list.add("1");
        return list;
    }

    private static String foo2() {// 没有显示抛出异常
        System.out.println("调用方法");
        // 模拟抛出异常
        System.out.println(1/0);
        return "1";
    }

    private static List<String> foo3() throws Exception {// 没有显示抛出异常
        System.out.println("调用方法");
        // 模拟抛出异常
        System.out.println(1/0);
        List<String> list = new ArrayList<>();
        list.add("1");
        return list;
    }
}