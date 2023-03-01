/**
 * com.aliyun.gts.gmall
 * Copyright(c) 2020-2021 All Rights Reserved.
 */
package io.entframework.kernel.cache.api.lock;

/**
 * 基于分布式中间件实现的读写锁
 */
public interface DistributedReadWriteLock {

    /**
     * 获取读锁对象
     * @return 读锁对象
     */
    DistributedLock readLock();

    /**
     * 获取写锁对象
     * @return 写锁对象
     */
    DistributedLock writeLock();

}
