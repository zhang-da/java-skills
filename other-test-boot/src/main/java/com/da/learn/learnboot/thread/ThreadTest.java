package com.da.learn.learnboot.thread;

public class ThreadTest {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        new Thread(() -> {
            System.out.println("hello");
        }).start();
    }
}
