/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis简单操作
 *
 */
public interface RedisSimpleDao extends RedisBaseDao {

    /**
     * 查询指定key的数据
     * @param key redisKey值
     * @param <V>
     * @return key所对应的数据
     */
    <V> V get(String key);

    /**
     * 批量查询在指定keys数组中的数据
     * @param keys redisKey数组
     * @param prefix redisKey的前缀
     * @param count 一次批量提交的命令中命令的个数
     * @return keys数组所对应的数据列表
     */
    List<?> batchGetAsList(List<String> keys, String prefix, int count);

    /**
     * 批量查询在指定keys数组中的数据
     * @param keys redisKey数组
     * @param prefix redisKey的前缀
     * @param count 一次批量提交的命令中命令的个数
     * @return keys数组所对应的数据Map集合
     */
    Map<String, Object> batchGetAsMap(List<String> keys, String prefix, int count);

    /**
     * 设置普通数据到redis中 在后续不设置过期时间的情况下，该数据将永不过期
     * @param key redisKey值
     * @param value 存储到redis中的数据
     * @param <V>
     */
    <V> void set(String key, V value);

    /**
     * 批量设置普通数据到redis中 在后续不设置过期时间的情况下，这些数据将永不过期
     * @param prefix redisKey前缀
     * @param values 待批量插入到数据库中的数据
     * @param <V>
     */
    <V> void set(String prefix, Map<String, V> values);

    /**
     * 设置普通数据到redis中 带有指定的存活时间
     * @param key redisKey值
     * @param value 存储到redis中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit 数据存活时间的时间单位
     * @param <V>
     */
    <V> void set(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 设置普通数据到redis中 带有指定的存活时间
     * @param prefix redisKey前缀
     * @param values 待批量插入到数据库中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit 数据存活时间的时间单位
     * @param <V>
     */
    <V> void set(String prefix, Map<String, V> values, long timeToLive, TimeUnit timeUnit);

    /**
     * 尝试设置普通数据到redis中，如果数据已存在，则返回false 在后续不设置过期时间的情况下，该数据将永不过期
     * @param key redisKey值
     * @param value 存储到redis中的数据
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> boolean trySet(String key, V value);

    /**
     * 尝试设置普通数据到redis中，如果数据已存在，则返回false 在后续不设置过期时间的情况下，该数据将永不过期
     * @param prefix redisKey前缀
     * @param values 待批量插入到数据库中的数据
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> Map<String, Boolean> trySet(String prefix, Map<String, V> values);

    /**
     * 尝试设置普通数据到redis中，如果数据已存在，则返回false 带有指定的存活时间
     * @param key redisKey值
     * @param value 存储到redis中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit 数据存活时间的时间单位
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 尝试设置普通数据到redis中，如果数据已存在，则返回false 带有指定的存活时间
     * @param prefix redisKey前缀
     * @param values 待批量插入到数据库中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit 数据存活时间的时间单位
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> Map<String, Boolean> trySet(String prefix, Map<String, V> values, long timeToLive, TimeUnit timeUnit);

    /**
     * 获取指定key的数据，随后将删除该key
     * @param key redisKey值
     * @return key所对应的数据
     */
    <V> V getAndDelete(String key);

}
