package com.da.learn.ipalert.job;

import com.da.learn.ipalert.handler.IpAlertHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.*;

@Component
@Slf4j
public class JobApplicationRunner implements ApplicationRunner {

    @Resource
    private IpAlertHandler ipAlertHandler;

    @Value("${task.initDelay}")
    private Long initDelay;

    @Value("${task.delay}")
    private Long delay;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduledExecutorService taskExecutor = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "my_schedule_pool");
                        thread.setDaemon(true);
                        return thread;
                    }
                }
        );
        try {
            Runnable peersUpdateTask = new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("START：{}", LocalDateTime.now().toString());
                        ipAlertHandler.handle();
                        log.info("END：{}", LocalDateTime.now().toString());
                    } catch (Exception e) {
                        log.error("异常：{}", e);
                    }
                }
            };
            taskExecutor.scheduleWithFixedDelay(
                    peersUpdateTask,
                    initDelay,
                    delay,
                    TimeUnit.MILLISECONDS
            );

            while (true) {
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
