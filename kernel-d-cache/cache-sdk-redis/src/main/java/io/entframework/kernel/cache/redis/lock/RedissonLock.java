/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.lock;

import com.google.common.base.Throwables;
import io.entframework.kernel.cache.api.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 基于redisson实现的DistributedLock，实际上是redisson RLock的代理
 *
 */
@Slf4j
public class RedissonLock implements DistributedLock {

    private final RLock rLock;

    public RedissonLock(RLock rLock) {
        this.rLock = rLock;
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime) throws InterruptedException {
        return rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        return rLock.tryLock(waitTime, leaseTime, timeUnit);
    }

    @Override
    public void unLock() {
        try {
            rLock.unlock();
        }
        catch (Exception ex) {
            log.warn("failed to unLock redis lock: {}, cause: {}", rLock.getName(),
                    Throwables.getStackTraceAsString(ex));
        }
    }

    @Override
    public boolean isLocked() {
        return rLock.isLocked();
    }

}
