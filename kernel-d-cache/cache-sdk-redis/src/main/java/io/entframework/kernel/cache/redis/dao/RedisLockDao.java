/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import io.entframework.kernel.cache.api.lock.DistributedCountDownLatch;
import io.entframework.kernel.cache.api.lock.DistributedLock;
import io.entframework.kernel.cache.api.lock.DistributedReadWriteLock;

/**
 * 基于Redis的锁操作
 */
public interface RedisLockDao extends RedisBaseDao {

    /**
     * 返回锁对象
     * @param key redisKey值
     * @return 锁对象
     */
    DistributedLock getLock(String key);

    /**
     * 返回读写锁对象
     * @param key redisKey值
     * @return 锁对象
     */
    DistributedReadWriteLock getReadWriteLock(String key);

    /**
     * 获取CountDownLatch
     * @param key redisKey值
     * @return RedisCountDownLatch
     */
    DistributedCountDownLatch getCountDownLatch(String key);

}
