/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.entframework.kernel.cache.redis.dao.RedisSimpleDao;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBatch;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于redisson的redis简单操作
 */
public class RedissonSimpleDao extends BaseRedissonDao implements RedisSimpleDao {
    public RedissonSimpleDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public <V> V get(String key) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public List<?> batchGetAsList(List<String> keys, String prefix, int count) {
        if (keys == null) {
            return Collections.emptyList();
        }

        // 不满足拆分条件
        if (keys.size() <= count) {
            RBatch rBatch = redissonClient.createBatch();
            keys.forEach(key -> {
                String rkey = this.getRedisKey(prefix, key);
                rBatch.getBucket(rkey).getAsync();
            });

            return rBatch.execute().getResponses();
        }

        // 请求参数量太大，拆分执行多次请求
        return SplitterUtils.split(keys, count).stream().flatMap(subKeys -> {
            RBatch rBatch = redissonClient.createBatch();
            subKeys.forEach(key -> {
                String rkey = this.getRedisKey(prefix, key);
                rBatch.getBucket(rkey).getAsync();
            });

            return rBatch.execute().getResponses().stream();
        }).toList();
    }

    @Override
    public Map<String, Object> batchGetAsMap(List<String> keys, String prefix, int count) {
        if (keys == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> resultMap = Maps.newHashMap();

        // 不满足拆分条件
        if (keys.size() <= count) {
            RBatch rBatch = redissonClient.createBatch();
            keys.forEach(key -> {
                String rkey = this.getRedisKey(prefix, key);
                rBatch.getBucket(rkey).getAsync();
            });

            List<?> resultList = rBatch.execute().getResponses();
            for (int i = 0; i < keys.size(); i++) {
                Object value = resultList.get(i);
                if (value != null) {
                    resultMap.put(keys.get(i), resultList.get(i));
                }
            }
        } else {
            SplitterUtils.split(keys, count).forEach(subKeys -> {
                List<String> ss = (List<String>) subKeys;
                RBatch rBatch = redissonClient.createBatch();
                subKeys.forEach(key -> {
                    String rkey = this.getRedisKey(prefix, key);
                    rBatch.getBucket(rkey).getAsync();
                });

                List<?> resultList = rBatch.execute().getResponses();
                for (int i = 0; i < ss.size(); i++) {
                    Object value = resultList.get(i);
                    if (value != null) {
                        resultMap.put(ss.get(i), resultList.get(i));
                    }
                }
            });
        }

        return resultMap;
    }

    @Override
    public <V> void set(String key, V value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public <V> void set(String prefix, Map<String, V> values) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }

        if (values.size() == 1) {
            String key = values.keySet().iterator().next();
            this.set(this.getRedisKey(prefix, key), values.get(key));
            return;
        }

        RBatch rBatch = redissonClient.createBatch();
        for (Map.Entry<String, V> entry : values.entrySet()) {
            rBatch.getBucket(this.getRedisKey(prefix, entry.getKey())).setAsync(entry.getValue());
        }
        rBatch.execute();
    }

    @Override
    public <V> void set(String key, V value, long timeToLive, TimeUnit timeUnit) {
        redissonClient.getBucket(key).set(value, timeToLive, timeUnit);
    }

    @Override
    public <V> void set(String prefix, Map<String, V> values, long timeToLive, TimeUnit timeUnit) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }

        if (values.size() == 1) {
            String key = values.keySet().iterator().next();
            this.set(this.getRedisKey(prefix, key), values.get(key), timeToLive, timeUnit);
            return;
        }

        RBatch rBatch = redissonClient.createBatch();
        for (String key : values.keySet()) {
            rBatch.getBucket(key).setAsync(values.get(this.getRedisKey(prefix, key)), timeToLive, timeUnit);
        }
        rBatch.execute();
    }

    @Override
    public <V> boolean trySet(String key, V value) {
        return redissonClient.getBucket(key).setIfAbsent(value);
    }

    @Override
    public <V> Map<String, Boolean> trySet(String prefix, Map<String, V> values) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }

        if (values.size() == 1) {
            String key = values.keySet().iterator().next();
            boolean rt = this.trySet(this.getRedisKey(prefix, key), values.get(key));
            return Map.of(key, rt);
        }

        RBatch rBatch = redissonClient.createBatch();
        List<String> keys = Lists.newArrayList(values.keySet());
        for (String key : keys) {
            rBatch.getBucket(this.getRedisKey(prefix, key)).setIfAbsentAsync(values.get(key));
        }

        List<?> rts = rBatch.execute().getResponses();
        Map<String, Boolean> result = Maps.newHashMap();
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), (Boolean) rts.get(i));
        }
        return result;
    }


    @Override
    public <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit) {
        return redissonClient.getBucket(key).trySet(value, timeToLive, timeUnit);
    }

    @Override
    public <V> Map<String, Boolean> trySet(String prefix, Map<String, V> values, long timeToLive, TimeUnit timeUnit) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }

        if (values.size() == 1) {
            String key = values.keySet().iterator().next();
            boolean rt = this.trySet(this.getRedisKey(prefix, key), values.get(key), timeToLive, timeUnit);
            return Map.of(key, rt);
        }

        RBatch rBatch = redissonClient.createBatch();
        List<String> keys = Lists.newArrayList(values.keySet());
        for (String key : keys) {
            rBatch.getBucket(this.getRedisKey(prefix, key)).trySetAsync(values.get(key), timeToLive, timeUnit);
        }

        List<?> rts = rBatch.execute().getResponses();
        Map<String, Boolean> result = Maps.newHashMap();
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), (Boolean) rts.get(i));
        }
        return result;
    }

    @Override
    public <V> V getAndDelete(String key) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.getAndDelete();
    }

    /**
     * 根据key值获取完整的redisKey
     *
     * @param prefix redisKey前缀
     * @param key    key值
     * @return 完整的redisKey
     */
    private String getRedisKey(String prefix, String key) {
        if (prefix == null || StringUtils.isBlank(key)) {
            return key;
        }

        if (key.startsWith(prefix)) {
            return key;
        }

        return prefix + key;
    }
}
