package com.da.learn.ipalert.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private static volatile Map<String, String> cache = new ConcurrentHashMap<>();

    public static String get(CacheType cacheType) {
        return cache.get(cacheType.getKey());
    }

    public static void set(CacheType cacheType, String value) {
        cache.put(cacheType.getKey(), value);
    }

    public static void del(CacheType cacheType) {
        cache.remove(cacheType.getKey());
    }

    public enum CacheType {
        IP("ip"),
        WRONG_IP_TIMES("wrong_ip_times");

        private String key;
        CacheType(String key) {
            this.key = key;
        }

        private String getKey() {
            return key;
        }
    }


}
