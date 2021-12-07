package com.da.learn.learnboot.ftp;

import org.apache.commons.pool2.ObjectPool;

public interface PooledFtpProcessor<T> extends FtpProcessor {

    void setPool(ObjectPool<T> pool);

    void setHasInit(boolean hasInit);

    void destroy();
}
