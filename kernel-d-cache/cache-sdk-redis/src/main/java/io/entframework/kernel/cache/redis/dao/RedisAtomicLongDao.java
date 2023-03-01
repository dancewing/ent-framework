/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

/**
 * 基于Redis的原子类操作
 */
public interface RedisAtomicLongDao extends RedisBaseDao {

	/**
	 * 获取atomic值
	 * @param key redisKey值
	 * @return atomic值
	 */
	long get(String key);

	/**
	 * 设置atomic值
	 * @param key redisKey值
	 * @param value 新设定的值
	 */
	void set(String key, long value);

	/**
	 * 获取当前atomic值，然后加上指定的delta数值
	 * @param key redisKey值
	 * @param delta 加到atomic上的值
	 * @return 变化之前的atomic值
	 */
	long getAndAdd(String key, long delta);

	/**
	 * 先加上指定的delta数值，然后再返回增加后的新值
	 * @param key redisKey值
	 * @param delta 加到atomic上的值
	 * @return 变化之后的atomic值
	 */
	long addAndGet(String key, long delta);

	/**
	 * 获取当前值，然后设置为另外一个数值
	 * @param key redisKey值
	 * @param value 新设定的值
	 * @return 变化之前的atomic值
	 */
	long getAndSet(String key, long value);

	/**
	 * 先递增1，然后再返回递增后的新值
	 * @param key redisKey值
	 * @return 变化之后的atomic值
	 */
	long incrementAndGet(String key);

	/**
	 * 获取当前值，然后递增1
	 * @param key redisKey值
	 * @return 变化之前的atomic值
	 */
	long getAndIncrement(String key);

	/**
	 * 先递减1，然后再返回递减后的新值
	 * @param key redisKey值
	 * @return 变化之后的atomic值
	 */
	long decrementAndGet(String key);

	/**
	 * 获取当前值，然后递减1
	 * @param key redisKey值
	 * @return 变化之前的atomic值
	 */
	long getAndDecrement(String key);

	/**
	 * 获取当前值，然后删除
	 * @param key redisKey值
	 * @return key所对应的数据
	 */
	long getAndDelete(String key);

	/**
	 * Atomically sets the value to the given updated value only if the current value
	 * {@code ==} the expected value.
	 * @param expect the expected value
	 * @param update the new value
	 * @return true if successful; or false if the actual value was not equal to the
	 * expected value.
	 */
	boolean compareAndSet(String key, long expect, long update);

}
