package com.da.learn.utils.singleton;

/**
 * 静态代码块式
 * 类装载时初始化
 */
public class Singleton2 {
    private static Singleton2 singleton2;

    static {
        singleton2 = new Singleton2();
    }

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return singleton2;
    }
}
