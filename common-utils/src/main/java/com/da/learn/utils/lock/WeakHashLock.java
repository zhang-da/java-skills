package com.da.learn.utils.lock;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 弱引用锁，为每个独立的哈希值提供独立的锁功能
 * <p>
 * 哈希锁因为引入的分段锁来保证锁创建和销毁的同步，总感觉有点瑕疵，所以写了第三个锁来寻求更好的性能和更细粒度的锁。
 * 这个锁的思想是借助java的弱引用来创建锁，把锁的销毁交给jvm的垃圾回收，来避免额外的消耗。
 * <p>
 * 有点遗憾的是因为使用了ConcurrentHashMap作为锁的容器，所以没能真正意义上的摆脱分段锁。这个锁的性能比 HashLock 快10% 左右。
 */
public class WeakHashLock<T> {
    private ConcurrentHashMap<T, WeakLockRef<T, ReentrantLock>> lockMap = new ConcurrentHashMap<>();
    private ReferenceQueue<ReentrantLock> queue = new ReferenceQueue<>();

    public ReentrantLock get(T key) {
        if (lockMap.size() > 1000) {
            clearEmptyRef();
        }
        WeakReference<ReentrantLock> lockRef = lockMap.get(key);
        ReentrantLock lock = (lockRef == null ? null : lockRef.get());
        while (lock == null) {
            lockMap.putIfAbsent(key, new WeakLockRef<>(new ReentrantLock(), queue, key));
            lockRef = lockMap.get(key);
            lock = (lockRef == null ? null : lockRef.get());
            if (lock != null) {
                return lock;
            }
            clearEmptyRef();
        }
        return lock;
    }

    @SuppressWarnings("unchecked")
    private void clearEmptyRef() {
        Reference<? extends ReentrantLock> ref;
        while ((ref = queue.poll()) != null) {
            WeakLockRef<T, ? extends ReentrantLock> weakLockRef = (WeakLockRef<T, ? extends ReentrantLock>) ref;
            lockMap.remove(weakLockRef.key);
        }
    }

    private static final class WeakLockRef<T, K> extends WeakReference<K> {
        final T key;

        private WeakLockRef(K referent, ReferenceQueue<? super K> q, T key) {
            super(referent, q);
            this.key = key;
        }
    }
}
