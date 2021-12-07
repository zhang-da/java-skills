package com.da.utils.ftp;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpProcessorGenerator {

    private static final Logger logger = LoggerFactory.getLogger(FtpProcessorGenerator.class);

    public static <T> PooledFtpProcessor getDefaultPooledFtpProcessor(FtpProperties ftpProperties) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(6000);
        poolConfig.setSoftMinEvictableIdleTimeMillis(50000);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        ObjectPool<T> pool = new GenericObjectPool(new FtpClientPooledObjectFactory(ftpProperties), poolConfig);
        preLoadingObject(pool, ftpProperties.getInitialSize(), poolConfig.getMaxIdle());
        PooledFtpProcessor processor = new DefaultPooledFtpProcessor(ftpProperties);
        processor.setPool(pool);
        processor.setHasInit(true);
        return processor;
    }

    /**
     * 预加载连接到对象池中
     *
     * @param initialSize
     * @param maxIdle
     */
    private static <T> void preLoadingObject(ObjectPool<T> pool, Integer initialSize, int maxIdle) {
        //如果初始化大小为null或者小于等于0，则不执行逻辑
        if (null == initialSize || initialSize <= 0) {
            return;
        }
        int size = Math.min(initialSize, maxIdle);
        try {
            for (int i = 0; i < size; i++) {
                pool.addObject();
            }
        } catch (Exception e) {
            logger.error("预加载失败！{}", e);
        }
    }
}
