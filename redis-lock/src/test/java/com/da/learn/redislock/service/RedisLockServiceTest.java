package com.da.learn.redislock.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockServiceTest {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @Resource(name = "SingleRedisLockService")
    @Resource(name = "RedissonRedisLockService")
    private RedisLockService redisLockService;

    @Test
    public void testRedis() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        redisTemplate.opsForValue().get("key");
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }


    @Test
    public void testLock() {
        boolean result = redisLockService.getLock("TEST_LOCK", "LOCK_VALUE", 100000L, TimeUnit.MILLISECONDS);
        System.out.println(result);
    }

    @Test
    public void testLockAndUnlock() throws Exception {
        lockAndUnlock();

    }

    @Test
    public void testLockAndUnlockManyTimes() throws Exception {
        for (int i = 1; i <= 10; i++) {
            lockAndUnlock();
        }
    }

    @Test
    public void testLockAndUnlockManyThread() throws Exception {
        List<Thread> tList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 1; j <= 10; j++) {
                    try {
                        lockAndUnlock();
                    } catch (Exception e) {

                    }
                }
            });
            thread.start();
            tList.add(thread);
        }
        for (Thread thread : tList) {
            thread.join();
        }
        log.info("执行完毕");
    }

    private void lockAndUnlock() {
        String uuid = UUID.randomUUID().toString();
        StopWatch stopWatch1 = new StopWatch();
        StopWatch stopWatch2 = new StopWatch();
        try {

            stopWatch1.start();
            boolean lock = redisLockService.getLock("test1", uuid, 100000L, TimeUnit.MILLISECONDS);
            stopWatch1.stop();
            if (lock) {
                log.info("获取锁成功");
                Thread.sleep(10000L);
            } else {
                log.info("锁被他人获取");
                Thread.sleep(10000L);
            }
        } catch (InterruptedException e) {
            log.error("获取锁失败");
        } finally {
            stopWatch2.start();
            redisLockService.unlock("test1", uuid);
            stopWatch2.stop();
//            log.info("==========获取锁时间(毫秒)：{}", stopWatch1.getTotalTimeMillis());
//            log.info("==========释放锁时间(毫秒)：{}", stopWatch2.getTotalTimeMillis());
        }
    }


    /**
     * 使用redLock算法实现
     */
    @Test
    private void testRedLock() {
        Config config1 = new Config();

        config1.useSingleServer().setAddress("redis://172.29.1.180:5378")

                .setPassword("a123456").setDatabase(0);

        RedissonClient redissonClient1 = Redisson.create(config1);


        Config config2 = new Config();

        config2.useSingleServer().setAddress("redis://172.29.1.180:5379")

                .setPassword("a123456").setDatabase(0);

        RedissonClient redissonClient2 = Redisson.create(config2);


        Config config3 = new Config();

        config3.useSingleServer().setAddress("redis://172.29.1.180:5380")

                .setPassword("a123456").setDatabase(0);

        RedissonClient redissonClient3 = Redisson.create(config3);


        String resourceName = "REDLOCK";

        RLock lock1 = redissonClient1.getLock(resourceName);

        RLock lock2 = redissonClient2.getLock(resourceName);

        RLock lock3 = redissonClient3.getLock(resourceName);


        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

        boolean isLock;

        try {

            isLock = redLock.tryLock(500, 30000, TimeUnit.MILLISECONDS);

            System.out.println("isLock = "+isLock);

            if (isLock) {

                //TODO if get lock success, do something;

                Thread.sleep(30000);

            }

        } catch (Exception e) {

        } finally {

            // 无论如何, 最后都要解锁

            System.out.println("");

            redLock.unlock();

        }
    }

}