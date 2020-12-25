package com.da.learn.learnboot.cache;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineTest {

    private static LoadingCache<String, String> cache = Caffeine.newBuilder()
            //最大个数限制
            .maximumSize(256L)
            //初始化容量
            .initialCapacity(1)
            //访问后过期（包括读和写）
            .expireAfterAccess(2, TimeUnit.DAYS)
            //写后过期
            .expireAfterWrite(2, TimeUnit.HOURS)
            //写后自动异步刷新
            .refreshAfterWrite(1, TimeUnit.HOURS)
            //记录下缓存的一些统计数据，例如命中率等
            .recordStats()
            //cache对缓存写的通知回调
            .writer(new CacheWriter<Object, Object>() {
                @Override
                public void write(@Nonnull Object key, @Nonnull Object value) {
                    log.info("key={}, CacheWriter write", key);
                }

                @Override
                public void delete(@Nonnull Object key, @Nullable Object value, @Nonnull RemovalCause cause) {
                    log.info("key={}, cause={}, CacheWriter delete", key, cause);
                }
            })
            //使用CacheLoader创建一个LoadingCache
            .build(new CacheLoader<String, String>() {
                //同步加载数据
                @Nullable
                @Override
                public String load(@Nonnull String key) throws Exception {
                    return "value_" + key;
                }

                //异步加载数据
                @Nullable
                @Override
                public String reload(@Nonnull String key, @Nonnull String oldValue) throws Exception {
                    return "value_" + key;
                }
            });

}
