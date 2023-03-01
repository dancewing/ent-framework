/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao.impl;

import io.entframework.kernel.cache.redis.dao.RedisAtomicLongDao;
import org.redisson.api.RedissonClient;

/**
 * 基于redisson实现的原子类操作
 *
 */
public class RedissonAtomicLongDao extends BaseRedissonDao implements RedisAtomicLongDao {

    public RedissonAtomicLongDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public long get(String key) {
        return redissonClient.getAtomicLong(key).get();
    }

    @Override
    public void set(String key, long value) {
        redissonClient.getAtomicLong(key).set(value);
    }

    @Override
    public long getAndAdd(String key, long delta) {
        return redissonClient.getAtomicLong(key).getAndAdd(delta);
    }

    @Override
    public long addAndGet(String key, long delta) {
        return redissonClient.getAtomicLong(key).addAndGet(delta);
    }

    @Override
    public long getAndSet(String key, long value) {
        return redissonClient.getAtomicLong(key).getAndSet(value);
    }

    @Override
    public long incrementAndGet(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }

    @Override
    public long getAndIncrement(String key) {
        return redissonClient.getAtomicLong(key).getAndIncrement();
    }

    @Override
    public long decrementAndGet(String key) {
        return redissonClient.getAtomicLong(key).decrementAndGet();
    }

    @Override
    public long getAndDecrement(String key) {
        return redissonClient.getAtomicLong(key).getAndDecrement();
    }

    @Override
    public long getAndDelete(String key) {
        return redissonClient.getAtomicLong(key).getAndDelete();
    }

    @Override
    public boolean compareAndSet(String key, long expect, long update) {
        return redissonClient.getAtomicLong(key).compareAndSet(expect, update);
    }

}
