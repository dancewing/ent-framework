/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis.dao.impl;

import io.entframework.kernel.cache.redis.dao.RedisSetDao;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于redisson实现的redis set
 *
 */
public class RedissonSetDao extends BaseRedissonDao implements RedisSetDao {

	public RedissonSetDao(RedissonClient redissonClient) {
		super(redissonClient);
	}

	@Override
	public <V> Set<V> getSet(String key) {
		return redissonClient.getSet(key);
	}

	@Override
	public <V> boolean contains(String key, V value) {
		return redissonClient.getSet(key).contains(value);
	}

	@Override
	public <V> boolean containsAll(String key, Collection<? extends V> values, int count) {
		if (CollectionUtils.isEmpty(values)) {
			return false;
		}

		RSet<V> rSet = redissonClient.getSet(key);
		if (values.size() <= count) {
			return rSet.containsAll(values);
		}

		return SplitterUtils.split(values, count).stream().allMatch(rSet::containsAll);
	}

	@Override
	public <V> Iterator<V> iterator(String key) {
		return iterator(key, 10);
	}

	@Override
	public <V> Iterator<V> iterator(String key, int count) {
		RSet<V> rSet = redissonClient.getSet(key);
		return rSet.iterator(10);
	}

	@Override
	public <V> boolean add(String key, V value) {
		RSet<V> rSet = redissonClient.getSet(key);
		return rSet.add(value);
	}

	@Override
	public <V> boolean addAll(String key, Collection<? extends V> values, int count) {
		if (CollectionUtils.isEmpty(values)) {
			return false;
		}

		RSet<V> rSet = redissonClient.getSet(key);
		if (values.size() <= count) {
			return rSet.addAll(values);
		}

		return SplitterUtils.split(values, count).stream().map(rSet::addAll).findAny().isPresent();
	}

	@Override
	public <V> V removeRandom(String key) {
		RSet<V> rSet = redissonClient.getSet(key);
		return rSet.removeRandom();
	}

	@Override
	public <V> Set<V> removeRandom(String key, int count) {
		RSet<V> rSet = redissonClient.getSet(key);
		return rSet.removeRandom(count);
	}

	@Override
	public <V> boolean remove(String key, V value) {
		RSet<V> rSet = redissonClient.getSet(key);
		return rSet.remove(value);
	}

	@Override
	public <V> boolean removeAll(String key, Collection<? extends V> values, int count) {
		if (CollectionUtils.isEmpty(values)) {
			return false;
		}

		RSet<V> rSet = redissonClient.getSet(key);
		if (values.size() <= count) {
			return rSet.removeAll(values);
		}

		return SplitterUtils.split(values, count).stream().map(rSet::removeAll).findAny().isPresent();
	}

}
