package com.da.learn.leetcode.lfu;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {

    // key 到 val 的映射
    private HashMap<String, String> keyToVal;
    // key 到 freq 的映射
    private HashMap<String, Integer> keyToFreq;
    // freq 到 key 列表的映射
    private HashMap<Integer, LinkedHashSet<String>> freqToKeys;
    //记录最小的频次
    private int minFreq;
    // 记录LFU缓存最大容量
    private int cap;

    // 构造容量为 capacity 的缓存
    public LFUCache(int capacity) {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        this.cap = capacity;
        this.minFreq = 0;
    }
    // 在缓存中查询 key
    public String get(String key) {
        if (!keyToVal.containsKey(key)) {
            return null;
        }
        // 增加 key 对应的 freq
        increaseFreq(key);
        return keyToVal.get(key);
    }
    // 将 key 和 val 存入缓存
    public void put(String key, String val) {
        if (this.cap <= 0) return;

        // 若key已存在，修改对应的value即可
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, val);
            // key 对应的Freq加1
            increaseFreq(key);
            return;
        }

        //key 不存在 需要插入
        //容量已满需要淘汰一个freq最小的key
        if (this.cap <= keyToVal.size()) {
            removeMinFreqKey();
        }
        //插入key和value  对应的freq为1
        keyToVal.put(key, val);
        keyToFreq.put(key, 1);
        freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqToKeys.get(1).add(key);
        //此时最小的freq肯定是1
        this.minFreq = 1;
    }


    //因为removeMinFreqKey是在put方法需要插入新key时调用，minFreq会变为1，所以这里不用改变minFreq
    private void removeMinFreqKey() {
        // freq最小的key列表
        LinkedHashSet<String> keyList = freqToKeys.get(this.minFreq);
        // 最先被插入的key就是该被淘汰的key
        String deletedKye = keyList.iterator().next();
        keyList.remove(deletedKye);
        if (keyList.isEmpty()) {
            freqToKeys.remove(this.minFreq);
        }
        keyToVal.remove(deletedKye);
        keyToFreq.remove(deletedKye);

    }

    private void increaseFreq(String key) {
        Integer freq = keyToFreq.get(key);
        keyToFreq.put(key, freq + 1);
        //将key从freq对应的列表中删除
        freqToKeys.get(freq).remove(key);
        //将key加入freq+1对应列表中
        freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqToKeys.get(freq + 1).add(key);
        //如果freq对应的列表空了，移除
        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            // 如果这个freq敲好是minFreq, 更新minFreq
            if (freq == this.minFreq) {
                this.minFreq++;
            }
        }
    }
}
