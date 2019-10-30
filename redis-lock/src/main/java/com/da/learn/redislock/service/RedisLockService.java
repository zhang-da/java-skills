package com.da.learn.redislock.service;

import java.util.concurrent.TimeUnit;

public interface RedisLockService {

    /**
     * 获取redis锁
     *
     * @param lockName
     * @param lockValue
     * @param leaseTime 超时自动释放时间
     * @param unit      时间单位
     * @return true:获取成功
     */
    boolean getLock(String lockName, String lockValue, long leaseTime, TimeUnit unit);

    /**
     *
     * @param lockName
     * @param lockValue
     */
    void unlock(String lockName, String lockValue);

}
