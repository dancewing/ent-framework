/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import java.util.concurrent.TimeUnit;

/**
 * Redis基础操作
 */
public interface RedisBaseDao {

    /**
     * 获取指定key的ttl
     * @param key redisKey值
     * @return 指定redisKey的ttl
     */
    long ttl(String key);

    /**
     * 根据指定key前缀，获取key的集合
     * @param prefix redisKey值前缀
     * @return redisKey值集合
     */
    Iterable<String> getKeys(String prefix);

    /**
     * 根据指定key前缀，获取key的集合，count值指定每批获取的数量大小
     * @param prefix redisKey值前缀
     * @param count 每次请求从redis中批量获取keys的数量，count小于等于0时，默认为10，最大值为100
     * @return redisKey值集合
     */
    Iterable<String> getKeys(String prefix, int count);

    /**
     * 设置指定key的数据存活时间
     * @param key redisKey值
     * @param timeToLive 数据存活时间
     * @param timeUnit 数据存活时间的时间单位
     * @return 当key值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expire(String key, long timeToLive, TimeUnit timeUnit);

    /**
     * 设置指定key的数据过期时间点
     * @param key redisKey值
     * @param timestamp 数据的过期时间点
     * @return 当key值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expireAt(String key, long timestamp);

    /**
     * 清除指定key的过期时间信息
     * @param key redisKey值
     * @return 当key值所对应的数据存在，且过期时间清除成功时，返回true
     */
    boolean clearExpire(String key);

    /**
     * 删除指定key的数据
     * @param keys redisKey值
     * @param count 一次批量操作的最大数量
     * @return 当key值所对应的数据存在，且删除成功时，返回true
     */
    long delete(int count, String... keys);

    /**
     * 删除指定前缀key的数据
     * @param prefix redisKey值前缀
     * @return 实际删除的数量
     */
    long deleteByPrefix(String prefix);

    /**
     * 查询指定的redis keys
     * @param count 一次批量操作的最大数量
     * @param names 待验证的names
     * @return 实际存在在redis中的key的数量
     */
    long countExists(int count, String... names);

}
