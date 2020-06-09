package com.da.learn.ipalert.job;

import com.da.learn.ipalert.handler.IpAlertHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.*;

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

    public static void main(String[] args) {
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
                    System.out.println("hello");
                }
            };
            taskExecutor.scheduleWithFixedDelay(
                    peersUpdateTask,
                    1000L,
                    1000L,
                    TimeUnit.MILLISECONDS
            );
            Thread.sleep(10000000L);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
