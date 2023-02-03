/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 基于Redis的Map相关操作
 *
 */
public interface RedisMapDao extends RedisBaseDao {
    /**
     * 获取Map对象
     *
     * @param key redisKey值
     * @param <V> value
     * @return key所对应的Map
     */
    <V> Map<String, V> getMap(String key);

    /**
     * 获取Map中指定的元素
     *
     * @param key        redisKey值
     * @param elementKey 元素key值
     * @param <V>        value
     * @return 返回实际存储的元素
     */
    <V> V get(String key, String elementKey);

    /**
     * 批量获取Map中指定的元素
     *
     * @param key         redisKey值
     * @param elementKeys 元素key值集合
     * @param <V> value
     * @return 返回实际存储的元素数据
     */
    <V> Map<String, V> getAll(String key, Set<String> elementKeys);

    /**
     * 向Map中设置元素
     *
     * @param key        redisKey值
     * @param elementKey 元素key值
     * @param value      新设置的元素
     * @param <V> value
     * @return 如果在设置之前，已存在相关元素，则返回该元素；反之返回null
     */
    <V> V put(String key, String elementKey, V value);

    /**
     * 如果元素不存在，则向Map中设置元素
     *
     * @param key        redisKey值
     * @param elementKey 元素key值
     * @param value      新设置的元素
     * @param <V> value
     * @return 如果元素不存在，则返回null；否则返回已存在的元素
     */
    <V> V putIfAbsent(String key, String elementKey, V value);

    /**
     * 向Map中批量设置元素
     *
     * @param key   redisKey值
     * @param map   待设置的元素集合
     * @param count 一次批量操作的最大数据数量
     * @param <V> value
     */
    <V> void putAll(String key, Map<String, ? extends V> map, int count);

    /**
     * 将Redis map中的数据copy到另外一个map结构中
     * 1、如果fromKey不存在，则返回false
     * 2、如果fromKey不是map结构，则抛出redis的操作异常
     * 3、如果toKey已存在，但不是map结构，则抛出redis的操作异常
     *
     * @param fromKey       待复制的redis map的key值
     * @param toKey         复制目的地redis map的key值
     * @param deleteFromKey 复制之后是否删除fromKey值
     * @return 操作结果
     */
    boolean copyOf(String fromKey, String toKey, boolean deleteFromKey);

    /**
     * 删除Map中指定的元素
     *
     * @param key        redisKey值
     * @param elementKey 元素key值
     * @param <V>  value
     * @return 如果元素存在，则返回该元素；否则返回null
     */
    <V> V remove(String key, String elementKey);

    /**
     * 删除Map中所有的元素
     *
     * @param key redisKey值
     * @return 返回被删除的elementsKey的数量
     */
    boolean removeAll(String key);

    /**
     * 批量删除Map中指定的元素
     *
     * @param key         redisKey值
     * @param elementKeys 元素key值集合
     * @param count       一次批量操作的最大数据数量
     * @return 返回删除是否成功
     */
    long removeAll(String key, Collection<String> elementKeys, int count);
}
