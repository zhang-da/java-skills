package com.da.learn.learnboot.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissonTest {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress("127.0.0.1:6369", "127.0.0.1:6379", "127.0.0.1:6389")
                .setMasterName("masterName")
                .setPassword("password").setDatabase(0);
        RedissonClient redissonClient = Redisson.create(config);
// 还可以getFairLock(), getReadWriteLock()
        RLock redLock = redissonClient.getLock("REDLOCK_KEY");
        boolean isLock;
        try {
            isLock = redLock.tryLock();
            // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
            isLock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
            if (isLock) {
                //TODO if get lock success, do something;
            }
        } catch (Exception e) {
        } finally {
            // 无论如何, 最后都要解锁
            redLock.unlock();
        }
    }

    public static void singleServer() {
        // 构造redisson实现分布式锁必要的Config
        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.29.1.180:5379").setPassword("a123456").setDatabase(0);
        // 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);
        // 设置锁定资源名称
        RLock disLock = redissonClient.getLock("DISLOCK");
        boolean isLock;
        try {
            //尝试获取分布式锁
            isLock = disLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
            if (isLock) {
                //TODO if get lock success, do something;
                Thread.sleep(15000);
            }

        } catch (Exception e) {

        } finally {
            // 无论如何, 最后都要解锁
            disLock.unlock();
        }
    }

    public static void sentinelServers() {
// 构造redisson实现分布式锁必要的Config
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress(
                "redis://172.29.3.245:26378", "redis://172.29.3.245:26379", "redis://172.29.3.245:26380")
                .setMasterName("mymaster")
                .setPassword("a123456").setDatabase(0);
        // 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);
        // 设置锁定资源名称
        RLock disLock = redissonClient.getLock("DISLOCK");
        boolean isLock;
        try {
            //尝试获取分布式锁
            isLock = disLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
            if (isLock) {
                //TODO if get lock success, do something;
                Thread.sleep(15000);
            }

        } catch (Exception e) {

        } finally {
            // 无论如何, 最后都要解锁
            disLock.unlock();
        }
    }

    public static void t3() {
        // 构造redisson实现分布式锁必要的Config
        Config config = new Config();
        config.useClusterServers().addNodeAddress(
                "redis://172.29.3.245:6375", "redis://172.29.3.245:6376", "redis://172.29.3.245:6377",
                "redis://172.29.3.245:6378", "redis://172.29.3.245:6379", "redis://172.29.3.245:6380")
                .setPassword("a123456").setScanInterval(5000);
        // 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);
        // 设置锁定资源名称
        RLock disLock = redissonClient.getLock("DISLOCK");
        boolean isLock;
        try {
            //尝试获取分布式锁
            isLock = disLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
            if (isLock) {
                //TODO if get lock success, do something;
                Thread.sleep(15000);
            }

        } catch (Exception e) {

        } finally {
            // 无论如何, 最后都要解锁
            disLock.unlock();
        }
    }
}
