package com.da.learn.learnboot.threadinfo;

import java.util.Arrays;
import java.util.List;

/**
 * 使用Lambda表达式并行计算
 */
public class LambdaTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5,6);
        LambdaTest test = new LambdaTest();
        int result = test.add(list);
        System.out.println("结果：" + result);
    }

    public int add(List<Integer> list) {
        list.parallelStream().forEach(System.out::println);
        return list.parallelStream().mapToInt(i -> i).sum();
    }
}
