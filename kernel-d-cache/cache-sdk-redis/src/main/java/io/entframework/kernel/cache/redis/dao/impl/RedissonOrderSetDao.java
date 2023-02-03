/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis.dao.impl;


import io.entframework.kernel.cache.redis.dao.RedisOrderSetDao;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于redisson实现的redis 顺序集合（非redis中的zset）
 *
 */
public class RedissonOrderSetDao extends BaseRedissonDao implements RedisOrderSetDao {
    public RedissonOrderSetDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public <V> Set<V> getSet(String key) {
        return redissonClient.getSortedSet(key);
    }

    @Override
    public <V> boolean contains(String key, V value) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.contains(value);
    }

    @Override
    public <V> boolean containsAll(String key, Collection<? extends V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }

        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        if (values.size() <= count) {
            return rSortedSet.containsAll(values);
        }

        return SplitterUtils.split(values, count).stream().allMatch(rSortedSet::containsAll);
    }

    @Override
    public <V> Iterator<V> iterator(String key) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.iterator();
    }

    @Override
    public <V> boolean add(String key, V value) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.add(value);
    }

    @Override
    public <V> boolean addAll(String key, Collection<? extends V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }

        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        if (values.size() <= count) {
            return rSortedSet.addAll(values);
        }

        return SplitterUtils.split(values, count).stream().anyMatch(rSortedSet::addAll);
    }

    @Override
    public <V> boolean remove(String key, V value) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.remove(value);
    }

    @Override
    public <V> boolean removeAll(String key, Collection<? extends V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }

        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        if (values.size() <= count) {
            return rSortedSet.removeAll(values);
        }

        return SplitterUtils.split(values, count).stream().anyMatch(rSortedSet::removeAll);
    }
}
