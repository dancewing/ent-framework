/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.cache.redis.dao.impl;

import io.entframework.kernel.cache.api.model.DistributedScoredEntry;
import io.entframework.kernel.cache.redis.dao.RedisZScoreSetDao;
import io.entframework.kernel.cache.redis.util.DataConverter;
import io.entframework.kernel.rule.util.SplitterUtils;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * 基于redisson实现的redis zset相关操作
 *
 */
public class RedissonZScoreSetDao extends BaseRedissonDao implements RedisZScoreSetDao {

    public RedissonZScoreSetDao(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public int size(String key) {
        return redissonClient.getScoredSortedSet(key).size();
    }

    @Override
    public int count(String key, double startScore, boolean startScoreInclusive, double endScore,
            boolean endScoreInclusive) {
        return redissonClient.getScoredSortedSet(key).count(startScore, startScoreInclusive, endScore,
                endScoreInclusive);
    }

    @Override
    public <V> Double getScore(String key, V element) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return rScoredSortedSet.getScore(element);
    }

    @Override
    public <V> Iterator<V> iterator(String key) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return rScoredSortedSet.iterator();
    }

    @Override
    public <V> Iterator<V> iterator(String key, String pattern) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return rScoredSortedSet.iterator(pattern);
    }

    @Override
    public <V> Collection<V> valueRange(String key, int fromIndex, int toIndex, boolean reverse) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return reverse ? rScoredSortedSet.valueRangeReversed(fromIndex, toIndex)
                : rScoredSortedSet.valueRange(fromIndex, toIndex);
    }

    @Override
    public <V> Collection<V> valueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
            boolean endScoreInclusive, boolean reverse) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return reverse
                ? rScoredSortedSet.valueRangeReversed(startScore, startScoreInclusive, endScore, endScoreInclusive)
                : rScoredSortedSet.valueRange(startScore, startScoreInclusive, endScore, endScoreInclusive);
    }

    @Override
    public <V> Collection<V> valueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
            boolean endScoreInclusive, int offset, int limit, boolean reverse) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return reverse
                ? rScoredSortedSet.valueRangeReversed(startScore, startScoreInclusive, endScore, endScoreInclusive,
                        offset, limit)
                : rScoredSortedSet.valueRange(startScore, startScoreInclusive, endScore, endScoreInclusive, offset,
                        limit);
    }

    @Override
    public <V> Collection<DistributedScoredEntry<V>> entryRange(String key, int fromIndex, int toIndex,
            boolean reverse) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        Collection<ScoredEntry<V>> dataList = reverse ? rScoredSortedSet.entryRangeReversed(fromIndex, toIndex)
                : rScoredSortedSet.entryRange(fromIndex, toIndex);

        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }

        return dataList.stream().map(DataConverter::converter).toList();
    }

    @Override
    public <V> Collection<DistributedScoredEntry<V>> entryRange(String key, double startScore,
            boolean startScoreInclusive, double endScore, boolean endScoreInclusive, boolean reverse) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        Collection<ScoredEntry<V>> dataList = reverse
                ? rScoredSortedSet.entryRangeReversed(startScore, startScoreInclusive, endScore, endScoreInclusive)
                : rScoredSortedSet.entryRange(startScore, startScoreInclusive, endScore, endScoreInclusive);

        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }

        return dataList.stream().map(DataConverter::converter).toList();
    }

    @Override
    public <V> Collection<DistributedScoredEntry<V>> entryRange(String key, double startScore,
            boolean startScoreInclusive, double endScore, boolean endScoreInclusive, int offset, int limit,
            boolean reverse) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        Collection<ScoredEntry<V>> dataList = reverse
                ? rScoredSortedSet.entryRangeReversed(startScore, startScoreInclusive, endScore, endScoreInclusive,
                        offset, limit)
                : rScoredSortedSet.entryRange(startScore, startScoreInclusive, endScore, endScoreInclusive, offset,
                        limit);

        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }

        return dataList.stream().map(DataConverter::converter).toList();
    }

    @Override
    public <V> boolean add(String key, V element, double score) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return rScoredSortedSet.add(score, element);
    }

    @Override
    public <V> int addAll(String key, Map<V, Double> elements, int count) {
        if (CollectionUtils.isEmpty(elements)) {
            return 0;
        }

        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        if (elements.size() <= count) {
            return rScoredSortedSet.addAll(elements);
        }

        return SplitterUtils.split(elements, count).stream().map(subMap -> rScoredSortedSet.addAll(subMap))
                .reduce(Integer::sum).orElse(0);
    }

    @Override
    public <V> Double addScore(String key, V element, double delta) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return rScoredSortedSet.addScore(element, delta);
    }

    @Override
    public <V> boolean remove(String key, V element) {
        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        return rScoredSortedSet.remove(element);
    }

    @Override
    public <V> boolean removeAll(String key, Collection<V> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }

        RScoredSortedSet<V> rScoredSortedSet = redissonClient.getScoredSortedSet(key);
        if (values.size() <= count) {
            return rScoredSortedSet.removeAll(values);
        }

        return SplitterUtils.split(values, count).stream().map(subValues -> rScoredSortedSet.removeAll(subValues))
                .anyMatch(r -> r);
    }

    @Override
    public int removeByRank(String key, int startIndex, int endIndex) {
        return redissonClient.getScoredSortedSet(key).removeRangeByRank(startIndex, endIndex);
    }

    @Override
    public int removeByScore(String key, double startScore, boolean startScoreInclusive, double endScore,
            boolean endScoreInclusive) {
        return redissonClient.getScoredSortedSet(key).removeRangeByScore(startScore, startScoreInclusive, endScore,
                endScoreInclusive);
    }

}
