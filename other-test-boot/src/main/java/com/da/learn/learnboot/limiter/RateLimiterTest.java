package com.da.learn.learnboot.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class RateLimiterTest {
    //令牌限流，每秒生成10个令牌
    private static RateLimiter tokenRateLimiter = RateLimiter.create(10.0);

    public static void main(String[] args) {
        for (int i = 0; i < 300; i++) {
            miaosha();
        }
    }

    private static void miaosha() {
        //每次抢购操作，都会持续尝试1秒
        if (tokenRateLimiter.tryAcquire(1, TimeUnit.SECONDS)) {
            //开启线程抢购
            System.out.println("SUCCESS====开始抢购");
        } else {
            System.out.println("FAIL======被拦截");
        }
    }
}
