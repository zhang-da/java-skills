package com.da.learn.learnboot.threadinfo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("新建线程 开始执行");
        Thread.sleep(2000);
        return "新建线程 返回结果";
    }

    public static void main(String[] args) throws Exception {
        CallableThread ct = new CallableThread();
        FutureTask<String> task = new FutureTask<>(ct);
        Thread t = new Thread(task);
        t.start();
        System.out.println("调用完成");
        String result = task.get();
        System.out.println("执行结果" + result);
    }
}
