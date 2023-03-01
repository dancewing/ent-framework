/**
 * com.aliyun.gts.gmall
 * Copyright(c) 2020-2021 All Rights Reserved.
 */
package io.entframework.kernel.cache.api.lock;

import java.util.concurrent.TimeUnit;

/**
 * 基于分布式中间件实现的CountDownLatch
 */
public interface DistributedCountDownLatch {

	/**
	 * 等待至CountDownLatch被释放
	 * @throws InterruptedException
	 */
	void await() throws InterruptedException;

	/**
	 * 尝试等待至CountDownLatch被释放
	 * @param timeout 尝试等待时长
	 * @param unit 时间单位
	 * @return
	 * @throws InterruptedException
	 */
	boolean await(long timeout, TimeUnit unit) throws InterruptedException;

	/**
	 * CountDownLatch进行减1
	 */
	void countDown();

	/**
	 * 获取当前CountDownLatch的值
	 * @return
	 */
	long getCount();

	/**
	 * 尝试将CountDownLatch设置为指定的数值（初始化）
	 * @param count 初始化值
	 * @return 是否设置成功
	 */
	boolean trySetCount(long count);

}
