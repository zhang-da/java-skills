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
     */
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean getLock(String lockName, String lockValue, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            return lock.tryLock(500, leaseTime, unit);
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
