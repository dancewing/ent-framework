/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.impl;

import io.entframework.kernel.cache.api.lock.DistributedCountDownLatch;
import io.entframework.kernel.cache.api.lock.DistributedLock;
import io.entframework.kernel.cache.api.lock.DistributedReadWriteLock;
import io.entframework.kernel.cache.api.model.DistributedScoredEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作的facade
 */
@Slf4j
public class RedisCacheManager<V> extends AbstractRedisCacheManager<V> {
    public RedisCacheManager(String prefix) {
        super(prefix);
    }

    @Override
    public Iterable<String> getKeys(String prefix, int count) {
        String redisKeyPrefix = this.getRedisKey(prefix);
        return redisSimpleDao.getKeys(redisKeyPrefix, count);
    }

    @Override
    public boolean expire(String key, long timeToLive) {
        return this.expire(key, timeToLive, timeUnit);
    }

    @Override
    public boolean expire(String key, long timeToLive, TimeUnit timeUnit) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.expire(redisKey, timeToLive, timeUnit);
    }

    @Override
    public boolean expireAt(String key, long timestamp) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.expireAt(redisKey, timestamp);
    }

    @Override
    public boolean clearExpire(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.clearExpire(redisKey);
    }

    @Override
    public long ttl(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.ttl(redisKey);
    }

    @Override
    public boolean hasKey(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.ttl(redisKey) > -2;
    }

    @Override
    public V get(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.get(redisKey);
    }

    @Override
    public List<?> batchGetAsList(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }

        return redisSimpleDao.batchGetAsList(keys, prefix, batchSize);
    }

    @Override
    public Map<String, Object> batchGetAsMap(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }

        return redisSimpleDao.batchGetAsMap(keys, prefix, batchSize);
    }

    @Override
    public boolean existsNull(String key) {
        key += ":null";
        String redisKey = this.getRedisKey(key);

        try {
            return redisSimpleDao.countExists(batchSize, redisKey) > 0;
        } catch (Exception ex) {
            log.error("redis exception: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public void set(String key, V value) {
        String redisKey = this.getRedisKey(key);
        redisSimpleDao.set(redisKey, value);
    }

    @Override
    public void set(Map<String, V> values) {
        redisSimpleDao.set(prefix, values);
    }

    @Override
    public void set(String key, V value, long timeToLive) {
        this.set(key, value, timeToLive, timeUnit);
    }

    @Override
    public void set(String key, V value, long timeToLive, TimeUnit timeUnit) {
        String redisKey = this.getRedisKey(key);
        redisSimpleDao.set(redisKey, value, timeToLive, timeUnit);
    }

    @Override
    public void set(Map<String, V> values, long timeToLive) {
        this.set(values, timeToLive, timeUnit);
    }

    @Override
    public void set(Map<String, V> values, long timeToLive, TimeUnit timeUnit) {
        redisSimpleDao.set(prefix, values, timeToLive, timeUnit);
    }

    @Override
    public void setNull(String key, long timeToLive) {
        this.setNull(key, timeToLive, timeUnit);
    }

    @Override
    public void setNull(String key, long timeToLive, TimeUnit timeUnit) {
        key += ":null";
        String redisKey = this.getRedisKey(key);

        try {
            redisSimpleDao.set(redisKey, true, timeToLive, timeUnit);
        } catch (Exception ex) {
            log.error("redis exception: {}", ex.getMessage());
        }
    }

    @Override
    public boolean trySet(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.trySet(redisKey, value);
    }

    @Override
    public Map<String, Boolean> trySet(Map<String, V> values) {
        return redisSimpleDao.trySet(prefix, values);
    }

    @Override
    public boolean trySet(String key, V value, long timeToLive) {
        return this.trySet(key, value, timeToLive, timeUnit);
    }

    @Override
    public boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit) {
        String redisKey = this.getRedisKey(key);
        return redisSimpleDao.trySet(redisKey, value, timeToLive, timeUnit);
    }

    @Override
    public Map<String, Boolean> trySet(Map<String, V> values, long timeToLive) {
        return this.trySet(values, timeToLive, timeUnit);
    }

    @Override
    public Map<String, Boolean> trySet(Map<String, V> values, long timeToLive, TimeUnit timeUnit) {
        return redisSimpleDao.trySet(prefix, values, timeToLive, timeUnit);
    }

    @Override
    public long delete(String... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            return 0;
        }

        String[] redisKeys = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            redisKeys[i] = this.getRedisKey(keys[i]);
        }
        return redisSimpleDao.delete(batchSize, redisKeys);
    }

    @Override
    public long delete(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0;
        }

        String[] redisKeys = keys.stream().map(this::getRedisKey).toList().toArray(new String[keys.size()]);
        return redisSimpleDao.delete(batchSize, redisKeys);
    }

    @Override
    public long deleteByPrefix(String prefix) {
        String redisPrefix = this.getRedisKey(prefix);
        return redisSimpleDao.deleteByPrefix(redisPrefix);
    }

    @Override
    public Map<String, V> getMap(String key) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.getMap(redisKey);
    }

    @Override
    public V getMapValue(String key, String elementKey) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.get(redisKey, elementKey);
    }

    @Override
    public Map<String, V> getAllMapValue(String key, Set<String> elementKeys) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.getAll(redisKey, elementKeys);
    }

    @Override
    public V putMapValue(String key, String elementKey, V value) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.put(redisKey, elementKey, value);
    }

    @Override
    public V putMapValueIfAbsent(String key, String elementKey, V value) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.putIfAbsent(redisKey, elementKey, value);
    }

    @Override
    public void putAllMapValue(String key, Map<String, ? extends V> map) {
        String redisKey = this.getRedisKey(key);
        redisMapDao.putAll(redisKey, map, batchSize);
    }

    @Override
    public boolean copyOfMap(String fromKey, String toKey, boolean deleteFromKey) {
        String redisFromKey = this.getRedisKey(fromKey);
        String redisToKey = this.getRedisKey(toKey);
        return redisMapDao.copyOf(redisFromKey, redisToKey, deleteFromKey);
    }

    @Override
    public V removeMapValue(String key, String elementKey) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.remove(redisKey, elementKey);
    }

    @Override
    public boolean removeAllMapValue(String key) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.removeAll(redisKey);
    }

    @Override
    public long removeAllMapValue(String key, Collection<String> elementKeys) {
        String redisKey = this.getRedisKey(key);
        return redisMapDao.removeAll(redisKey, elementKeys, batchSize);
    }

    @Override
    public List<V> getList(String key) {
        String redisKey = this.getRedisKey(key);
        return redisListDao.getList(redisKey);
    }

    @Override
    public V getListValue(String key, int index) {
        String redisKey = this.getRedisKey(key);
        return redisListDao.get(redisKey, index);
    }

    @Override
    public List<V> getListValueByRange(String key, int toIndex) {
        String redisKey = this.getRedisKey(key);
        return redisListDao.range(redisKey, toIndex);
    }

    @Override
    public List<V> getListValueByRange(String key, int fromIndex, int toIndex) {
        String redisKey = this.getRedisKey(key);
        return redisListDao.range(redisKey, fromIndex, toIndex);
    }

    @Override
    public void addListValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        redisListDao.add(redisKey, value);
    }

    @Override
    public void addAllListValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        redisListDao.addAll(redisKey, values, batchSize);
    }

    @Override
    public void addAllListValue(String key, int index, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        redisListDao.addAll(redisKey, index, values, batchSize);
    }

    @Override
    public void trimList(String key, int fromIndex, int toIndex) {
        String redisKey = this.getRedisKey(key);
        redisListDao.trim(redisKey, fromIndex, toIndex);
    }

    @Override
    public boolean removeListValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisListDao.remove(redisKey, value);
    }

    @Override
    public boolean removeAllListValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisListDao.removeAll(redisKey, values, batchSize);
    }

    @Override
    public Set<V> getSet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.getSet(redisKey);
    }

    @Override
    public boolean containsSetValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.contains(redisKey, value);
    }

    @Override
    public boolean containsAllSetValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.containsAll(redisKey, values, batchSize);
    }

    @Override
    public Iterator<V> iteratorSet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.iterator(redisKey);
    }

    @Override
    public boolean addSetValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.add(redisKey, value);
    }

    @Override
    public boolean addAllSetValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.addAll(redisKey, values, batchSize);
    }

    @Override
    public V removeSetRandom(String key) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.removeRandom(redisKey);
    }

    @Override
    public Set<V> removeSetRandom(String key, int count) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.removeRandom(redisKey, count);
    }

    @Override
    public boolean removeSetValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.remove(redisKey, value);
    }

    @Override
    public boolean removeAllSetValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisSetDao.removeAll(redisKey, values, batchSize);
    }

    @Override
    public Set<V> getOrderSet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.getSet(redisKey);
    }

    @Override
    public boolean containsOrderSetValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.contains(redisKey, value);
    }

    @Override
    public boolean containsAllOrderSetValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.containsAll(redisKey, values, batchSize);
    }

    @Override
    public Iterator<V> iteratorOrderSet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.iterator(redisKey);
    }

    @Override
    public boolean addOrderSetValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.add(redisKey, value);
    }

    @Override
    public boolean addAllOrderSetValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.addAll(redisKey, values, batchSize);
    }

    @Override
    public boolean removeOrderSetValue(String key, V value) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.remove(redisKey, value);
    }

    @Override
    public boolean removeAllOrderSetValue(String key, Collection<? extends V> values) {
        String redisKey = this.getRedisKey(key);
        return redisOrderSetDao.removeAll(redisKey, values, batchSize);
    }

    @Override
    public int countOfZSet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.size(redisKey);
    }

    @Override
    public int countOfZSet(String key, double startScore, boolean startScoreInclusive, double endScore,
                           boolean endScoreInclusive) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.count(redisKey, startScore, startScoreInclusive, endScore, endScoreInclusive);
    }

    @Override
    public Double getZSetScore(String key, V element) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.getScore(redisKey, element);
    }

    @Override
    public Iterator<V> iteratorZSet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.iterator(redisKey);
    }

    @Override
    public Iterator<V> iteratorZSet(String key, String pattern) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.iterator(redisKey, pattern);
    }

    @Override
    public Collection<V> getZSetValueRange(String key, int fromIndex, int toIndex, boolean reverse) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.valueRange(redisKey, fromIndex, toIndex, reverse);
    }

    @Override
    public Collection<V> getZSetValueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
                                               boolean endScoreInclusive, boolean reverse) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.valueRange(redisKey, startScore, startScoreInclusive, endScore, endScoreInclusive, reverse);
    }

    @Override
    public Collection<V> getZSetValueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
                                               boolean endScoreInclusive, int offset, int limit, boolean reverse) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.valueRange(redisKey, startScore, startScoreInclusive, endScore, endScoreInclusive,
                offset, limit, reverse);
    }

    @Override
    public Collection<DistributedScoredEntry<V>> getZSetEntryRange(String key, int fromIndex, int toIndex, boolean reverse) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.entryRange(redisKey, fromIndex, toIndex, reverse);
    }

    @Override
    public Collection<DistributedScoredEntry<V>> getZSetEntryRange(String key, double startScore, boolean startScoreInclusive,
                                                                       double endScore, boolean endScoreInclusive, boolean reverse) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.entryRange(redisKey, startScore, startScoreInclusive, endScore, endScoreInclusive, reverse);
    }

    @Override
    public Collection<DistributedScoredEntry<V>> getZSetEntryRange(String key, double startScore, boolean startScoreInclusive, double endScore,
                                                                       boolean endScoreInclusive, int offset, int limit, boolean reverse) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.entryRange(redisKey, startScore, startScoreInclusive, endScore, endScoreInclusive, offset,
                limit, reverse);
    }

    @Override
    public boolean addZSetValue(String key, V element, double score) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.add(redisKey, element, score);
    }

    @Override
    public int addAllZSetValue(String key, Map<V, Double> elements) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.addAll(redisKey, elements, batchSize);
    }

    @Override
    public Double addZSetScore(String key, V element, double delta) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.addScore(redisKey, element, delta);
    }

    @Override
    public boolean removeZSetValue(String key, V element) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.remove(redisKey, element);
    }

    @Override
    public boolean removeAllZSetValue(String key, Collection<V> values) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.removeAll(redisKey, values, batchSize);
    }

    @Override
    public int removeZSetValueByRank(String key, int fromIndex, int toIndex) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.removeByRank(redisKey, fromIndex, toIndex);
    }

    @Override
    public int removeZSetValueByScore(String key, double startScore, boolean startScoreInclusive, double endScore,
                                      boolean endScoreInclusive) {
        String redisKey = this.getRedisKey(key);
        return redisZScoreSetDao.removeByScore(redisKey, startScore, startScoreInclusive, endScore, endScoreInclusive);
    }

    @Override
    public long atomicGet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.get(redisKey);
    }

    @Override
    public void atomicSet(String key, long value) {
        String redisKey = this.getRedisKey(key);
        redisAtomicLongDao.set(redisKey, value);
    }

    @Override
    public long atomicGetAndAdd(String key, long delta) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.getAndAdd(redisKey, delta);
    }

    @Override
    public long atomicAddAndGet(String key, long delta) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.addAndGet(redisKey, delta);
    }

    @Override
    public long atomicGetAndSet(String key, long value) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.getAndSet(redisKey, value);
    }

    @Override
    public long atomicGetAndDelete(String key) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.getAndDelete(redisKey);
    }

    @Override
    public long getAndIncrement(String key) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.getAndIncrement(redisKey);
    }

    @Override
    public long incrementAndGet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.incrementAndGet(redisKey);
    }

    @Override
    public long getAndDecrement(String key) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.getAndDecrement(redisKey);
    }

    @Override
    public long decrementAndGet(String key) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.decrementAndGet(redisKey);
    }

    @Override
    public boolean compareAndSet(String key, long expect, long update) {
        String redisKey = this.getRedisKey(key);
        return redisAtomicLongDao.compareAndSet(redisKey, expect, update);
    }

    @Override
    public DistributedLock getLock(String key) {
        String redisKey = this.getRedisKey(key);
        return redisLockDao.getLock(redisKey);
    }

    @Override
    public DistributedReadWriteLock getReadWriteLock(String key) {
        String redisKey = this.getRedisKey(key);
        return redisLockDao.getReadWriteLock(redisKey);
    }

    @Override
    public DistributedCountDownLatch getCountDownLatch(String key) {
        String redisKey = this.getRedisKey(key);
        return redisLockDao.getCountDownLatch(redisKey);
    }
}
