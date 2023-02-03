/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.memory;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.cache.api.CacheOperatorApi;

import java.util.*;

/**
 * 基于内存的缓存封装
 *
 * @date 2020/7/9 10:09
 */
public abstract class AbstractMemoryCacheOperator<T> implements CacheOperatorApi<T> {

    private final TimedCache<String, T> timedCache;

    private final String prefix;

    protected AbstractMemoryCacheOperator(TimedCache<String, T> timedCache, String prefix) {
        this.timedCache = timedCache;
        this.prefix = prefix;
    }

    @Override
    public void put(String key, T value) {
        timedCache.put(calcKey(key), value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        timedCache.put(calcKey(key), value, timeoutSeconds * 1000);
    }

    @Override
    public T get(String key) {
        // 如果用户在超时前调用了get(key)方法，会重头计算起始时间，false的作用就是不从头算
        return timedCache.get(calcKey(key), true);
    }

    @Override
    public void remove(String... key) {
        for (String itemKey : key) {
            timedCache.remove(calcKey(itemKey));
        }
    }

    @Override
    public void expire(String key, Long expiredSeconds) {
        T value = timedCache.get(calcKey(key), true);
        timedCache.put(calcKey(key), value, expiredSeconds * 1000);
    }

    @Override
    public boolean contains(String key) {
        return timedCache.containsKey(calcKey(key));
    }

    @Override
    public Collection<String> getAllKeys() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        ArrayList<String> keys = CollUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            // 去掉缓存key的common prefix前缀
            String key = cacheObjIterator.next().getKey();
            keys.add(CharSequenceUtil.removePrefix(key, getPrefix()));
        }
        return keys;
    }

    @Override
    public Collection<T> getAllValues() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        ArrayList<T> values = CollUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            values.add(cacheObjIterator.next().getValue());
        }
        return values;
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
        return prefix;
    }
}
