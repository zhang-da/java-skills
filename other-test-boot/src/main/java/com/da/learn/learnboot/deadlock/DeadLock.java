package com.da.learn.learnboot.deadlock;

import java.util.Arrays;
import java.util.List;

public class DeadLock {

    public static void main(String[] args) {
        final List<Integer> list1 = Arrays.asList(1, 2, 3);
        final List<Integer> list2 = Arrays.asList(4, 5, 6);
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list1) {
                    for (Integer i : list1) {
                        System.out.println(i);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (list2) {
                        for (Integer i : list2) {
                            System.out.println(i);
                        }
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list2) {
                    for (Integer i : list2) {
                        System.out.println(i);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (list1) {
                        for (Integer i : list1) {
                            System.out.println(i);
                        }
                    }
                }
            }
        }).start();
    }
}
