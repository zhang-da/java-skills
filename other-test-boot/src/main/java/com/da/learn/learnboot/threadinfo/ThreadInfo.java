package com.da.learn.learnboot.threadinfo;

import java.util.Map;

public class ThreadInfo {
    public static void main(String[] args) {
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = stackTrace.getKey();
            StackTraceElement[] stack = stackTrace.getValue();
            if (thread.equals(Thread.currentThread())) {
                continue;
            }
            System.out.println("\n线程：" + thread.getName() + "");
            for (StackTraceElement element : stack) {
                System.out.println("\t" + element + "");
            }
        }
    }
}
