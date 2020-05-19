package com.da.learn.utils.singleton;

/**
 * 静态内部类式
 * 延迟加在  避免线程不安全  同双重检查模式
 *
 * JVM在类的初始化阶段（即在Class被加载后，且被线程使用之前），会执行类的初始化。在
 * 执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化。
 *
 * Initialization On Demand Holder idiom
 */
public class Singleton6 {
    private Singleton6() {
    }

    private static class SingletonInstance {
        private static final Singleton6 SINGLETON_6 = new Singleton6();
    }

    public static Singleton6 getInstance() {
        return SingletonInstance.SINGLETON_6;
    }
}
