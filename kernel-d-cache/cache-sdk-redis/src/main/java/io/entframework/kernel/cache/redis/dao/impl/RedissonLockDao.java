/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao.impl;

import io.entframework.kernel.cache.api.lock.DistributedCountDownLatch;
import io.entframework.kernel.cache.api.lock.DistributedLock;
import io.entframework.kernel.cache.api.lock.DistributedReadWriteLock;
import io.entframework.kernel.cache.redis.dao.RedisLockDao;
import io.entframework.kernel.cache.redis.lock.RedissonCountDownLatch;
import io.entframework.kernel.cache.redis.lock.RedissonLock;
import io.entframework.kernel.cache.redis.lock.RedissonReadWriteLock;
import org.redisson.api.RedissonClient;

/**
 * 基于redisson实现的分布式锁
 *
 */
public class RedissonLockDao extends BaseRedissonDao implements RedisLockDao {

    public RedissonLockDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public DistributedLock getLock(String key) {
        return new RedissonLock(redissonClient.getLock(key));
    }

    @Override
    public DistributedReadWriteLock getReadWriteLock(String key) {
        return new RedissonReadWriteLock(redissonClient.getReadWriteLock(key));
    }

    @Override
    public DistributedCountDownLatch getCountDownLatch(String key) {
        return new RedissonCountDownLatch(redissonClient.getCountDownLatch(key));
    }

}
