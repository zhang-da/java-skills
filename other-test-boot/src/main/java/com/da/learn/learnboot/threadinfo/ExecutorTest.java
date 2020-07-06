package com.da.learn.learnboot.threadinfo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorTest {
    public static void main(String[] args) {
        //创建带有5个线程的线程池
        //返回的实际上是ExecutorService,而ExecutorService是Executor的子接口
        Executor threadPool = Executors.newFixedThreadPool(5);
        for(int i = 0 ;i < 10 ; i++) {
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" is running");
                }
            });
        }
    }
}
