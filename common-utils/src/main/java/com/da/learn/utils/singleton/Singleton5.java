package com.da.learn.utils.singleton;

/**
 * 懒汉式，双重检查模式
 * volatile很关键 防止重排序 未完成初始化(new Singleton()) 时被另一个线程调用
 */
public class Singleton5 {
    private static volatile Singleton5 singleton5;

    private Singleton5() {
    }

    public static Singleton5 getInstance() {
        if (singleton5 == null) {
            synchronized (Singleton5.class) {
                if (singleton5 == null) {
                    singleton5 = new Singleton5();
                }
            }
        }
        return singleton5;
    }
}
