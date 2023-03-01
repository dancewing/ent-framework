/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.lock;

import io.entframework.kernel.cache.api.lock.DistributedLock;
import io.entframework.kernel.cache.api.lock.DistributedReadWriteLock;
import org.redisson.api.RReadWriteLock;

/**
 * 基于redisson实现的DistributedReadWriteLock，实际上是redisson RReadWriteLock的代理
 */
public class RedissonReadWriteLock implements DistributedReadWriteLock {

    /** 读写锁的对象 */
    private RReadWriteLock lock;

    public RedissonReadWriteLock(RReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    public DistributedLock readLock() {
        return new RedissonLock(lock.readLock());
    }

    @Override
    public DistributedLock writeLock() {
        return new RedissonLock(lock.writeLock());
    }

}
