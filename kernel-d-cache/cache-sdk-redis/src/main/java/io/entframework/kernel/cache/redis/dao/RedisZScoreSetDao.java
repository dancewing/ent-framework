/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.dao;

import io.entframework.kernel.cache.api.model.DistributedScoredEntry;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 基于redis实现的zset相关操作
 *
 */
public interface RedisZScoreSetDao extends RedisBaseDao {

	/**
	 * 计算指定的redis zset大小
	 * @param key redisKey值
	 * @return 指定key值的redis zset大小
	 */
	int size(String key);

	/**
	 * 计算指定的redis zset从startScore到endScore之间的数据个数<br/>
	 * startScoreInclusive和endScoreInclusive分别用于指定是否包含初始和结束的score值
	 * @param key redisKey值
	 * @param startScore 开始位置的score值
	 * @param startScoreInclusive 是否包含开始位置的score值
	 * @param endScore 结束位置的score值
	 * @param endScoreInclusive 是否包含结束位置的score值
	 * @return 满足条件的元素个数
	 */
	int count(String key, double startScore, boolean startScoreInclusive, double endScore, boolean endScoreInclusive);

	/**
	 * 获取指定zset元素所对应的score值
	 * @param key redisKey值
	 * @param element zset中指定的数据
	 * @return 指定元素所对应的score值，如果元素不存在，则返回null
	 */
	<V> Double getScore(String key, V element);

	/**
	 * 获取指定的redis zset迭代器
	 * @param key redisKey值
	 * @return 指定的redis zset迭代器
	 */
	<V> Iterator<V> iterator(String key);

	/**
	 * 获取指定的redis zset迭代器，迭代器中的结果满足范式pattern
	 * @param key redisKey值
	 * @param pattern 结果的范式
	 * @return 指定的redis zset迭代器
	 */
	<V> Iterator<V> iterator(String key, String pattern);

	/**
	 * 按顺序/倒序获取指定的redis zset中，从fromIndex到toIndex的数据元素集合(仅包括数据)<br/>
	 * redis下标从0开始
	 * @param key redisKey值
	 * @param fromIndex 开始位置，包含
	 * @param toIndex 结束位置，包括
	 * @param reverse 是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
	 * @return 在指定范围内的redis zset数据元素集合
	 */
	<V> Collection<V> valueRange(String key, int fromIndex, int toIndex, boolean reverse);

	/**
	 * 按顺序/倒序获取指定redis zset中，分数值从startScore到endScore之间的数据元素集合(仅包括数据)<br/>
	 * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
	 * @param key redisKey值
	 * @param startScore 开始位置的score值
	 * @param startScoreInclusive 是否包含开始位置的score值
	 * @param endScore 结束位置的score值
	 * @param endScoreInclusive 是否包含结束位置的score值
	 * @param reverse 是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
	 * @return 在指定范围内的redis zset数据元素集合
	 */
	<V> Collection<V> valueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
			boolean endScoreInclusive, boolean reverse);

	/**
	 * 按顺序/倒序获取指定redis
	 * zset中，分数值从startScore到endScore之间的数据元素集合(仅包括数据)，返回结果中指定offset和limit<br/>
	 * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
	 * @param key redisKey值
	 * @param startScore 开始位置的score值
	 * @param startScoreInclusive 是否包含开始位置的score值
	 * @param endScore 结束位置的score值
	 * @param endScoreInclusive 是否包含结束位置的score值
	 * @param offset 数据起始位置的偏移量
	 * @param limit 返回数据元素集合最大的大小
	 * @param reverse 是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
	 * @return 在指定范围内的redis zset数据元素集合
	 */
	<V> Collection<V> valueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
			boolean endScoreInclusive, int offset, int limit, boolean reverse);

	/**
	 * 按顺序/倒序获取指定的redis zset中，从fromIndex到toIndex的实体元素集合(包括数据和分值)<br/>
	 * redis下标从0开始
	 * @param key redisKey值
	 * @param fromIndex 开始位置，包含
	 * @param toIndex 结束位置，包括
	 * @param reverse 是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
	 * @return 在指定范围内的redis zset实体元素集合
	 */
	<V> Collection<DistributedScoredEntry<V>> entryRange(String key, int fromIndex, int toIndex, boolean reverse);

	/**
	 * 按顺序/倒序获取指定redis zset中，分数值从startScore到endScore之间的实体元素集合(包括数据和分值)<br/>
	 * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
	 * @param key redisKey值
	 * @param startScore 开始位置的score值
	 * @param startScoreInclusive 是否包含开始位置的score值
	 * @param endScore 结束位置的score值
	 * @param endScoreInclusive 是否包含结束位置的score值
	 * @param reverse 是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
	 * @return 在指定范围内的redis zset实体元素集合
	 */
	<V> Collection<DistributedScoredEntry<V>> entryRange(String key, double startScore, boolean startScoreInclusive,
			double endScore, boolean endScoreInclusive, boolean reverse);

	/**
	 * 按顺序/倒序获取指定redis
	 * zset中，分数值从startScore到endScore之间的实体元素集合(包括数据和分值)，返回结果中指定offset和limit<br/>
	 * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
	 * @param key redisKey值
	 * @param startScore 开始位置的score值
	 * @param startScoreInclusive 是否包含开始位置的score值
	 * @param endScore 结束位置的score值
	 * @param endScoreInclusive 是否包含结束位置的score值
	 * @param offset 数据起始位置的偏移量
	 * @param limit 返回数据元素集合最大的大小
	 * @param reverse 是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
	 * @return 在指定范围内的redis zset实体元素集合
	 */
	<V> Collection<DistributedScoredEntry<V>> entryRange(String key, double startScore, boolean startScoreInclusive,
			double endScore, boolean endScoreInclusive, int offset, int limit, boolean reverse);

	/**
	 * 向zset中插入元素，如果元素element已存在，则更新其score值
	 * @param key redisKey值
	 * @param element 待插入的数据
	 * @param score 待插入的数据所对应的分值
	 * @return 是否插入成功
	 */
	<V> boolean add(String key, V element, double score);

	/**
	 * 向zset中批量插入元素，如果元素已存在，则更新其score值
	 * @param key redisKey值
	 * @param elements 待插入的元素集合
	 * @param count 一次批量操作的最大数据数量
	 * @return 成功新插入的数量（不包括已存在的元素）
	 */
	<V> int addAll(String key, Map<V, Double> elements, int count);

	/**
	 * 给相应的zset元素中的分值加上delta
	 * @param key redisKey值
	 * @param element zset中的元素
	 * @param delta 待加上分值上的变量
	 * @return 更新后的分值
	 */
	<V> Double addScore(String key, V element, double delta);

	/**
	 * 删除zset中指定的元素
	 * @param key redisKey值
	 * @param element 待删除的元素
	 * @return 是否删除成功
	 */
	<V> boolean remove(String key, V element);

	/**
	 * 批量删除zset中指定的元素
	 * @param key redisKey值
	 * @param values 待删除的元素集合
	 * @param count 一次批量操作的最大数据数量
	 * @return 是否删除成功
	 */
	<V> boolean removeAll(String key, Collection<V> values, int count);

	/**
	 * 删除指定范围内的zset中的元素
	 * @param key redisKey
	 * @param startIndex 开始序号，包含
	 * @param endIndex 结束序号，包含
	 * @return 删除数量
	 */
	int removeByRank(String key, int startIndex, int endIndex);

	/**
	 * 删除指定score值范围内的zeset中的元素
	 * @param key redisKey值
	 * @param startScore 开始位置的score值
	 * @param startScoreInclusive 是否包含开始位置的score值
	 * @param endScore 结束位置的score值
	 * @param endScoreInclusive 是否包含结束位置的score值
	 * @return 删除数量
	 */
	int removeByScore(String key, double startScore, boolean startScoreInclusive, double endScore,
			boolean endScoreInclusive);

}
