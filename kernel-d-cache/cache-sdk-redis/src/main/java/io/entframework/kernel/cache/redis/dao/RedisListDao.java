/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import java.util.Collection;
import java.util.List;

/**
 * 基于Redis的List相关操作
 */
public interface RedisListDao extends RedisBaseDao {

	/**
	 * 获取List对象
	 * @param key redisKey值
	 * @param <V>
	 * @return key所对应的List
	 */
	<V> List<V> getList(String key);

	/**
	 * 获取List中指定位置的元素
	 * @param key redisKey值
	 * @param index 指定的位置
	 * @param <V>
	 * @return key所对应的List指定位置的元素
	 */
	<V> V get(String key, int index);

	/**
	 * 获取list中下标[0, toIndex]之间的元素，闭区间
	 * @param key redisKey元素
	 * @param toIndex 截止的下标
	 * @param <V>
	 * @return 指定范围内的元素
	 */
	<V> List<V> range(String key, int toIndex);

	/**
	 * 获取list中下标[fromIndex, toIndex]之间的元素，闭区间
	 * @param key redisKey元素
	 * @param fromIndex 开始的下标
	 * @param toIndex 截止的下标
	 * @param <V>
	 * @return 指定范围内的元素
	 */
	<V> List<V> range(String key, int fromIndex, int toIndex);

	/**
	 * 向List中插入元素
	 * @param key redisKey值
	 * @param value 新插入的元素
	 * @param <V>
	 */
	<V> void add(String key, V value);

	/**
	 * 向List中的指定位置插入元素
	 * @param key redisKey值
	 * @param index 指定的位置
	 * @param value 新插入的元素
	 * @param <V>
	 */
	<V> void add(String key, int index, V value);

	/**
	 * 向List中批量插入元素
	 * @param key redisKey值
	 * @param values 新插入的元素集合
	 * @param count 一次批量操作最大插入的元素数量
	 * @param <V>
	 */
	<V> void addAll(String key, Collection<? extends V> values, int count);

	/**
	 * 向List中的指定位置批量插入元素
	 * @param key redisKey值
	 * @param index 指定的位置
	 * @param values 新插入的元素集合
	 * @param count 一次批量操作最大插入的元素数量
	 * @param <V>
	 */
	<V> void addAll(String key, int index, Collection<? extends V> values, int count);

	/**
	 * 对指定的List进行修剪，让该List只保留[fromIndex, toIndex]区间内的元素
	 * @param key redisKey值
	 * @param fromIndex 开始的下标
	 * @param toIndex 截止的下标
	 */
	void trim(String key, int fromIndex, int toIndex);

	/**
	 * 删除List中指定的元素
	 * @param key redisKey值
	 * @param value 待删除的元素
	 * @param <V>
	 * @return 是否删除成功
	 */
	<V> boolean remove(String key, V value);

	/**
	 * 删除List中指定位置的元素
	 * @param key redisKey值
	 * @param index 指定的位置
	 * @param <V>
	 * @return 是否删除成功
	 */
	<V> V remove(String key, int index);

	/**
	 * 删除List中指定的元素集合
	 * @param key redisKey值
	 * @param values 待删除的元素集合
	 * @param count 一次批量操作最大插入的元素数量
	 * @param <V>
	 * @return 是否删除成功
	 */
	<V> boolean removeAll(String key, Collection<? extends V> values, int count);

}
