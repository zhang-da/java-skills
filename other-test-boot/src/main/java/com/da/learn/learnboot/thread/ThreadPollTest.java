package com.da.learn.learnboot.thread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPollTest {

    private static Executor pool =
            new ThreadPoolExecutor(
                    100000,
                    100000,
                    100,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());


    public static void main(String[] args) {

        for (int i = 1; i <= 1000000; i++) {
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")) + " 1111线程"+ i +"准备开始");
            int finalI = i;
            pool.execute(() -> {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")) + " 3333线程" + finalI + "执行开始");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("出现错误");
                }
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")) + " 4444线程" + finalI + "执行结束");
            });
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")) + " 2222线程" + i +"调用结束");
        }

    }
}
