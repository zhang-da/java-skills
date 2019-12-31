package com.da.learn.utils.singleton;

/**
 * 恶汉式单例
 */
public class Singleton1 {
    private static Singleton1 singleton1 = new Singleton1();

    private Singleton1() {
}

    public static Singleton1 getInstance() {
        return singleton1;
    }
}
