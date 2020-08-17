package com.da.learn.leetcode.lru;

import java.util.LinkedHashMap;

public class LRUCacheUseWheel {
    int cap;
    LinkedHashMap<String, String> cache = new LinkedHashMap<>();

    public LRUCacheUseWheel(int cap) {
        this.cap = cap;
    }

    public String get(String key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(String key, String value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            makeRecently(key);
            return;
        }
        if (cache.size() >= this.cap) {
            String oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, value);
    }

    private void makeRecently(String key) {
        String val = cache.get(key);
        // 删除 key，重新插入到队尾
        cache.remove(key);
        cache.put(key, val);
    }
}
