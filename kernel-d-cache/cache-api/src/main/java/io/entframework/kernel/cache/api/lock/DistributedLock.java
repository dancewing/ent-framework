/**
 * com.aliyun.gts.gmall
 * Copyright(c) 2020-2021 All Rights Reserved.
 */
package io.entframework.kernel.cache.api.lock;

import java.util.concurrent.TimeUnit;

/**
 * 基于分布式中间件实现的分布式锁
 */
public interface DistributedLock {
    /**
     * 尝试获取分布式锁
     * 1、如果在waitTime时间内正常获取到锁，则返回true；反之返回false；
     * 2、在执行unLock方法之前，该锁最长的持有leaseTime时间，超时后将自动释放
     * 3、waitTime和leaseTime的时间单位默认为毫秒，可以选择在配置文件中修改默认配置
     *
     * @param waitTime  获取锁的最长等待时间
     * @param leaseTime 获取到锁之后，最长的持有时间
     * @return 成功获取锁，则返回true；否则返回false
     * @throws InterruptedException
     */
    boolean tryLock(long waitTime, long leaseTime) throws InterruptedException;

    /**
     * 尝试获取分布式锁
     * 1、如果在waitTime时间内正常获取到锁，则返回true；反之返回false；
     * 2、在执行unLock方法之前，该锁最长的持有leaseTime时间，超时后将自动释放
     *
     * @param waitTime  获取锁的最长等待时间
     * @param leaseTime 获取到锁之后，最长的持有时间
     * @param timeUnit  指定waitTime和leaseTime的时间单位
     * @return 成功获取锁，则返回true；否则返回false
     * @throws InterruptedException
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 释放锁
     * 线程获取到锁之后，必须在相应的finally中释放锁
     */
    void unLock();

    /**
     * 测试当前锁是否被任何线程所持有（包括当前线程和其他线程）
     *
     * @return 如果被任意线程持有，则返回true；反之返回false
     */
    boolean isLocked();
}
