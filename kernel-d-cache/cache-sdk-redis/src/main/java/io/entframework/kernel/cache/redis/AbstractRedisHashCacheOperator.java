/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.cache.redis;

import cn.hutool.core.collection.CollUtil;
import io.entframework.kernel.cache.api.CacheManager;
import io.entframework.kernel.cache.api.CacheOperatorApi;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 基于redis的缓存封装，hash结构
 *
 * @date 2020/7/9 10:09
 */
@SuppressWarnings("all")
public abstract class AbstractRedisHashCacheOperator<T> implements CacheOperatorApi<T> {

    private final CacheManager<T> cacheManager;

    public AbstractRedisHashCacheOperator(CacheManager<T> cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void put(String key, T value) {
        this.cacheManager.putMapValue(getPrefix(), key, value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        // 不能设置单个的过期时间
        this.put(key, value);
    }

    @Override
    public T get(String key) {
        return this.cacheManager.getMapValue(getPrefix(), key);
    }

    @Override
    public void remove(String... key) {
        this.cacheManager.removeAllMapValue(getPrefix(), Arrays.asList(key));
    }

    @Override
    public void expire(String key, Long expiredSeconds) {
        // 设置整个hash的
        this.cacheManager.expire(getPrefix(), expiredSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean contains(String key) {
        return this.cacheManager.getMapValue(getPrefix(), key) != null;
    }

    @Override
    public Collection<String> getAllKeys() {
        Map<String, T> values = this.cacheManager.getMap(getPrefix());
        if (values != null) {
            // 去掉缓存key的common prefix前缀
            return values.entrySet().stream().map(Object::toString).collect(Collectors.toSet());
        } else {
            return CollUtil.newHashSet();
        }
    }

    @Override
    public Collection<T> getAllValues() {
        Collection<String> allKeys = getAllKeys();
        Map<String, T> values = this.cacheManager.getMap(getPrefix());
        if (values != null) {
            return values.values().stream().collect(Collectors.toList());
        } else {
            return CollUtil.newArrayList();
        }
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        return this.cacheManager.getMap(getPrefix());
    }

    @Override
    public String getPrefix() {
        return this.cacheManager.getPrefix();
    }
}
