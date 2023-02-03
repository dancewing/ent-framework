/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.cache.api.CacheManager;
import io.entframework.kernel.cache.api.CacheOperatorApi;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 基于redis的缓存封装
 *
 * @date 2020/7/9 10:09
 */
public abstract class AbstractRedisCacheOperator<T> implements CacheOperatorApi<T> {


    private CacheManager<T> cacheManager;

    protected AbstractRedisCacheOperator(CacheManager<T> cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void put(String key, T value) {
        this.cacheManager.set(key, value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        this.cacheManager.set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public T get(String key) {
        return this.cacheManager.get(key);
    }

    @Override
    public void remove(String... key) {
        this.cacheManager.delete(key);
    }

    @Override
    public void expire(String key, Long expiredSeconds) {
        this.cacheManager.expire(key, expiredSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean contains(String key) {
        return this.cacheManager.hasKey(key);
    }

    @Override
    public Collection<String> getAllKeys() {
        Iterable<String> keys = this.cacheManager.getKeys("*", 100);
        if (keys != null) {
            // 去掉缓存key的common prefix前缀
            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(
                            keys.iterator(),
                            Spliterator.ORDERED)
                    , false).map(key -> CharSequenceUtil.removePrefix(key, getPrefix())).collect(Collectors.toSet());
        } else {
            return CollUtil.newHashSet();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> getAllValues() {
        List<String> keys = new ArrayList<>(getAllKeys());
        List<?> results = this.cacheManager.batchGetAsList(keys);
        List<T> answer = new ArrayList<>();
        results.forEach(o -> answer.add((T) o));
        return answer;
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        HashMap<String, T> results = MapUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }

    @Override
    public String getPrefix() {
        return this.cacheManager.getPrefix();
    }
}
