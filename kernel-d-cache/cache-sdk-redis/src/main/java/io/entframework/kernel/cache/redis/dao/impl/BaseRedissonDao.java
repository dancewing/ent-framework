/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao.impl;

import io.entframework.kernel.cache.redis.dao.RedisBaseDao;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 基于redisson实现的基础通用操作
 *
 */
public abstract class BaseRedissonDao implements RedisBaseDao {

	protected RedissonClient redissonClient;

	protected BaseRedissonDao(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	@Override
	public long ttl(String key) {
		return redissonClient.getKeys().remainTimeToLive(key);
	}

	@Override
	public Iterable<String> getKeys(String prefix) {
		return getKeys(prefix, 10);
	}

	@Override
	public Iterable<String> getKeys(String prefix, int count) {
		RKeys keys = redissonClient.getKeys();

		String redisKey = prefix + "*";
		if (count <= 0) {
			count = 10;
		}
		else if (count > 100) {
			count = 100;
		}

		return keys.getKeysByPattern(redisKey, count);
	}

	@Override
	public boolean expire(String key, long timeToLive, TimeUnit timeUnit) {
		return redissonClient.getKeys().expire(key, timeToLive, timeUnit);
	}

	@Override
	public boolean expireAt(String key, long timestamp) {
		return redissonClient.getKeys().expireAt(key, timestamp);
	}

	@Override
	public boolean clearExpire(String key) {
		return redissonClient.getKeys().clearExpire(key);
	}

	@Override
	public long delete(int count, String... keys) {
		if (ArrayUtils.isEmpty(keys)) {
			return 0;
		}

		RKeys rKeys = redissonClient.getKeys();
		if (keys.length <= count) {
			return rKeys.delete(keys);
		}

		return SplitterUtils.split(keys, count).stream().map(rKeys::delete).reduce(Long::sum).orElse(0L);
	}

	@Override
	public long deleteByPrefix(String prefix) {
		return redissonClient.getKeys().deleteByPattern(prefix + "*");
	}

	@Override
	public long countExists(int count, String... names) {
		if (ArrayUtils.isEmpty(names)) {
			return 0;
		}

		RKeys rKeys = redissonClient.getKeys();
		if (names.length <= count) {
			return rKeys.countExists(names);
		}

		return SplitterUtils.split(names, count).stream().map(rKeys::countExists).reduce(Long::sum).orElse(0L);
	}

}
