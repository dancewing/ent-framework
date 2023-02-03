/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis.dao.impl;

import io.entframework.kernel.cache.redis.dao.RedisListDao;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 基于redisson实现的redis list操作
 *
 */
public class RedissonListDao extends BaseRedissonDao implements RedisListDao {
    public RedissonListDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public <V> List<V> getList(String key) {
        return redissonClient.getList(key);
    }

    @Override
    public <V> V get(String key, int index) {
        RList<V> rList = redissonClient.getList(key);
        return rList.get(index);
    }

    @Override
    public <V> List<V> range(String key, int toIndex) {
        RList<V> rList = redissonClient.getList(key);
        return rList.range(toIndex);
    }

    @Override
    public <V> List<V> range(String key, int fromIndex, int endIndex) {
        RList<V> rList = redissonClient.getList(key);
        return rList.range(fromIndex, endIndex);
    }

    @Override
    public <V> void add(String key, V value) {
        RList<V> rList = redissonClient.getList(key);
        rList.add(value);
    }

    @Override
    public <V> void add(String key, int index, V value) {
        RList<V> rList = redissonClient.getList(key);
        rList.add(index, value);
    }

    @Override
    public <V> void addAll(String key, Collection<? extends V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }

        RList<V> rList = redissonClient.getList(key);
        if (values.size() <= count) {
            rList.addAll(values);
            return;
        }

        SplitterUtils.split(values, count).stream().forEach(subValues -> rList.addAll(subValues));
    }

    @Override
    public <V> void addAll(String key, int index, Collection<? extends V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }

        RList<V> rList = redissonClient.getList(key);
        if (values.size() <= count) {
            rList.addAll(index, values);
        }

        int newIndex = index;
        for (Collection<? extends V> subValues : SplitterUtils.split(values, count)) {
            rList.addAll(newIndex, values);
            newIndex += subValues.size();
        }
    }

    @Override
    public void trim(String key, int fromIndex, int toIndex) {
        RList<?> rList = redissonClient.getList(key);
        rList.trim(fromIndex, toIndex);
    }

    @Override
    public <V> boolean remove(String key, V value) {
        RList<V> rList = redissonClient.getList(key);
        return rList.remove(value);
    }

    @Override
    public <V> V remove(String key, int index) {
        RList<V> rList = redissonClient.getList(key);
        return rList.remove(index);
    }

    @Override
    public <V> boolean removeAll(String key, Collection<? extends V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }

        RList<V> rList = redissonClient.getList(key);
        if (values.size() <= count) {
            return rList.removeAll(values);
        }

        return SplitterUtils.split(values, count).stream().anyMatch(rList::removeAll);
    }
}
