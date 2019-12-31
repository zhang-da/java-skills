package com.da.learn.utils.singleton;

/**
 * 懒汉式，解决线程安全问题，效率低
 */
public class Singleton4 {
    private static Singleton4 singleton4;

    private Singleton4() {
    }

    public static synchronized Singleton4 getInstance() {
        if (singleton4 == null) {
            singleton4 = new Singleton4();
        }
        return singleton4;
    }
}
