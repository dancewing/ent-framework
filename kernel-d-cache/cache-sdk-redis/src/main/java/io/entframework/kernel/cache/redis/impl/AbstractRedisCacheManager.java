/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.impl;

import io.entframework.kernel.cache.api.CacheManager;
import io.entframework.kernel.cache.redis.dao.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Redis操作的facade
 */
@Slf4j
public abstract class AbstractRedisCacheManager<V> implements CacheManager<V> {

	protected RedisSimpleDao redisSimpleDao;

	protected RedisMapDao redisMapDao;

	protected RedisListDao redisListDao;

	protected RedisSetDao redisSetDao;

	protected RedisOrderSetDao redisOrderSetDao;

	protected RedisZScoreSetDao redisZScoreSetDao;

	protected RedisAtomicLongDao redisAtomicLongDao;

	protected RedisLockDao redisLockDao;

	/** redisKey值前缀 */
	@Getter
	protected String prefix;

	/** 时间单位 */
	protected TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	/** 做批量操作时，一次批量提交的命令中命令的个数 */
	protected int batchSize = 10;

	protected AbstractRedisCacheManager(String prefix) {
		if (StringUtils.isNotBlank(prefix)) {
			this.prefix = prefix;
		}
	}

	public AbstractRedisCacheManager<V> setRedisSimpleDao(RedisSimpleDao redisSimpleDao) {
		this.redisSimpleDao = redisSimpleDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisMapDao(RedisMapDao redisMapDao) {
		this.redisMapDao = redisMapDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisListDao(RedisListDao redisListDao) {
		this.redisListDao = redisListDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisSetDao(RedisSetDao redisSetDao) {
		this.redisSetDao = redisSetDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisOrderSetDao(RedisOrderSetDao redisOrderSetDao) {
		this.redisOrderSetDao = redisOrderSetDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisZScoreSetDao(RedisZScoreSetDao redisZScoreSetDao) {
		this.redisZScoreSetDao = redisZScoreSetDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisAtomicLongDao(RedisAtomicLongDao redisAtomicLongDao) {
		this.redisAtomicLongDao = redisAtomicLongDao;
		return this;
	}

	public AbstractRedisCacheManager<V> setRedisLockDao(RedisLockDao redisLockDao) {
		this.redisLockDao = redisLockDao;
		return this;
	}

	/**
	 * 根据key值获取完整的redisKey
	 * @param key key值
	 * @return 完整的redisKey
	 */
	protected String getRedisKey(String key) {
		if (prefix == null || StringUtils.isBlank(key)) {
			return key;
		}

		if (key.startsWith(prefix)) {
			return key;
		}

		return this.prefix + key;
	}

}
