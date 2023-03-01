/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于Redis的顺序集合（非redis中的zset）相关操作
 */
public interface RedisOrderSetDao extends RedisBaseDao {

    /**
     * 获取Redis 顺序的set对象
     * @param key redisKey值
     * @return key所对应的Set
     */
    <V> Set<V> getSet(String key);

    /**
     * 验证指定的数据是否在Redis set中存在
     * @param key redisKey值
     * @param value 待验证的数据
     * @return 如果存在，则返回true；反之返回false
     */
    <V> boolean contains(String key, V value);

    /**
     * 验证指定的数据集合是否在Redis set中存在
     * @param key redisKey值
     * @param values 待验证的数据
     * @param count 一次批量操作的最大数据数量
     * @return 如果全部存在，则返回true；反之返回false
     */
    <V> boolean containsAll(String key, Collection<? extends V> values, int count);

    /**
     * 获取Redis set的Iterator对象
     * @param key redisKey值
     * @return key所对应的Iterator
     */
    <V> Iterator<V> iterator(String key);

    /**
     * 向Redis set插入指定的数据
     * @param key redisKey值
     * @param value 待插入的数据
     * @return 插入成功返回true；否则返回false
     */
    <V> boolean add(String key, V value);

    /**
     * 向Redis set中批量插入数据
     * @param key redisKey值
     * @param values 待插入的数据集合
     * @param count 一次批量操作的最大数据数量
     * @return 如果插入成功，则返回true；否则返回false
     */
    <V> boolean addAll(String key, Collection<? extends V> values, int count);

    /**
     * 删除set中指定的元素
     * @param key redisKey值
     * @param value 待删除的元素
     * @return 指定的value存在，且删除成功时，返回true；否则返回false
     */
    <V> boolean remove(String key, V value);

    /**
     * 删除set中指定的元素集合
     * @param key redisKey值
     * @param values 待删除的元素集合
     * @param count 一次批量操作的最大数据数量
     * @return 是否删除成功
     */
    <V> boolean removeAll(String key, Collection<? extends V> values, int count);

}
