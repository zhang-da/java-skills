package com.da.learn.ipalert.job;

import com.da.learn.ipalert.handler.IpAlertHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
@Slf4j
public class Job {

    private static Executor pool =
            new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    @Resource
    private IpAlertHandler ipAlertHandler;

    @Scheduled(cron = "* * * * * ? ")
    public void runIpAlert() {
        try {
            pool.execute(() -> {
                log.info("触发：{}", LocalDateTime.now().toString());
//                ipAlertHandler.handle();
            });
        } catch (Throwable e) {
            log.error("出错了：{}", e);
        }
    }
}
