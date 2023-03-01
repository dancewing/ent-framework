/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis.lock;

import io.entframework.kernel.cache.api.lock.DistributedCountDownLatch;
import org.redisson.api.RCountDownLatch;

import java.util.concurrent.TimeUnit;

/**
 * 基于redisson实现的DistributedCountDownLatch
 *
 */
public class RedissonCountDownLatch implements DistributedCountDownLatch {

    private RCountDownLatch countDownLatch;

    public RedissonCountDownLatch(RCountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void await() throws InterruptedException {
        countDownLatch.await();
    }

    @Override
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return countDownLatch.await(timeout, unit);
    }

    @Override
    public void countDown() {
        countDownLatch.countDown();
    }

    @Override
    public long getCount() {
        return countDownLatch.getCount();
    }

    @Override
    public boolean trySetCount(long count) {
        return countDownLatch.trySetCount(count);
    }

}
