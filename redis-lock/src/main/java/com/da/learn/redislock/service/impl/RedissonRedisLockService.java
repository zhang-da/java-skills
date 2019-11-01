package com.da.learn.redislock.service.impl;

import com.da.learn.redislock.service.RedisLockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("RedissonRedisLockService")
public class RedissonRedisLockService implements RedisLockService {


    /**
     * 参考  org.redisson.spring.starter.RedissonAutoConfiguration
     * 可自己配置Config和RedissonClient
     * 使用的是普通单机算法  不是redLock算法
     */
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean getLock(String lockName, String lockValue, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            //如果没有获取锁  最多等待3000unit
            return lock.tryLock(3000L, unit.toMillis(leaseTime), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockName, String lockValue) {
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
    }
}
