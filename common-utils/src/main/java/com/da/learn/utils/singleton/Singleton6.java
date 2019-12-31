package com.da.learn.utils.singleton;

/**
 * 静态内部类式
 * 延迟加在  避免线程不安全  同双重检查模式
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
