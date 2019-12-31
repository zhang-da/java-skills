package com.da.learn.utils.singleton;

/**
 * 懒汉式，只能在单线程使用
 */
public class Singleton3 {
    private static Singleton3 singleton3;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (singleton3 == null) {
            singleton3 = new Singleton3();
        }
        return singleton3;
    }
}
