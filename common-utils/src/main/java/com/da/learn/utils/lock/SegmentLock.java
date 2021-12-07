package com.da.learn.utils.lock;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分段锁，系统提供一定数量的原始锁，根据传入对象的哈希值获取对应的锁并加锁
 * 注意：要锁的对象的哈希值如果发生改变，有可能导致锁无法成功释放!!!
 *
 * 借鉴concurrentHashMap的分段思想，先生成一定数量的锁，具体使用的时候再根据key来返回对应的lock。这是几个实现里最简单，性能最高
 */
public class SegmentLock<T> {
    private Integer segments = 16;//默认分段数量
    private final HashMap<Integer, ReentrantLock> lockMap = new HashMap<>();

    public SegmentLock() {
        init(null, false);
    }

    public SegmentLock(Integer counts, boolean fair) {
        init(counts, fair);
    }

    private void init(Integer counts, boolean fair) {
        if (counts != null) {
            segments = counts;
        }
        for (int i = 0; i < segments; i++) {
            lockMap.put(i, new ReentrantLock(fair));
        }
    }

    public void lock(T key) {
        ReentrantLock lock = lockMap.get(key.hashCode() % segments);
        lock.lock();
    }

    public void unlock(T key) {
        ReentrantLock lock = lockMap.get(key.hashCode() % segments);
        lock.unlock();
    }
}
