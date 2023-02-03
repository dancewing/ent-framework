/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis.dao.impl;


import io.entframework.kernel.cache.redis.dao.RedisMapDao;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 基于redisson实现的redis map
 *
 */
public class RedissonMapDao extends BaseRedissonDao implements RedisMapDao {
    public RedissonMapDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public <V> Map<String, V> getMap(String key) {
        return redissonClient.getMap(key);
    }

    @Override
    public <V> V get(String key, String elementKey) {
        RMap<String, V> rMap = redissonClient.getMap(key);
        return rMap.get(elementKey);
    }

    @Override
    public <V> Map<String, V> getAll(String key, Set<String> elementKeys) {
        if (CollectionUtils.isEmpty(elementKeys)) {
            return Collections.emptyMap();
        }

        if (elementKeys.size() == 1) {
            return get(key, elementKeys.iterator().next());
        }
        RMap<String, V> rMap = redissonClient.getMap(key);
        return rMap.getAll(elementKeys);
    }

    @Override
    public <V> V put(String key, String elementKey, V value) {
        RMap<String, V> rMap = redissonClient.getMap(key);
        return rMap.put(elementKey, value);
    }

    @Override
    public <V> V putIfAbsent(String key, String elementKey, V value) {
        RMap<String, V> rMap = redissonClient.getMap(key);
        return rMap.putIfAbsent(elementKey, value);
    }

    @Override
    public <V> void putAll(String key, Map<String, ? extends V> map, int count) {
        if (CollectionUtils.isEmpty(map)) {
            return;
        }

        RMap<String, V> rMap = redissonClient.getMap(key);
        rMap.putAll(map, count);
    }

    @Override
    public boolean copyOf(String fromKey, String toKey, boolean deleteFromKey) {
        RMap<String, Object> rMap = redissonClient.getMap(fromKey);
        Set<String> keys = rMap.keySet();
        if (CollectionUtils.isEmpty(keys)) {
            return false;
        }

        Map<String, Object> redisInfo = rMap.getAll(keys);
        this.putAll(toKey, redisInfo, 10);

        if (deleteFromKey) {
            rMap.delete();
        }
        return true;
    }

    @Override
    public <V> V remove(String key, String elementKey) {
        RMap<String, V> rMap = redissonClient.getMap(key);
        return rMap.remove(elementKey);
    }

    @Override
    public boolean removeAll(String key) {
        RMap<String, Object> rMap = redissonClient.getMap(key);
        return rMap.delete();
    }

    @Override
    public long removeAll(String key, Collection<String> elementKeys, int count) {
        if (CollectionUtils.isEmpty(elementKeys)) {
            return 0;
        }

        RMap<String, Object> rMap = redissonClient.getMap(key);
        if (elementKeys.size() <= count) {
            return rMap.fastRemove(elementKeys.toArray(new String[0]));
        }

        return SplitterUtils.splitAsArray(elementKeys, String.class, count).stream()
                .map(rMap::fastRemove)
                .reduce(Long::sum).orElse(0L);
    }
}
