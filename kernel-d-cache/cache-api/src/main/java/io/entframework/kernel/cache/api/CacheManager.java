/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.api;

import io.entframework.kernel.cache.api.lock.DistributedCountDownLatch;
import io.entframework.kernel.cache.api.lock.DistributedLock;
import io.entframework.kernel.cache.api.lock.DistributedReadWriteLock;
import io.entframework.kernel.cache.api.model.DistributedScoredEntry;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CacheManager
 *
 */
public interface CacheManager<V> {
    /**
     * 获取cache的key值前缀
     *
     * @return
     */
    String getPrefix();

    /**
     * 根据指定key前缀，获取相应的key集合。每次请求cache时，都会尝试获取count数量的数据（而不是一次性或者一个个的获取）
     *
     * @param prefix cache key值前缀
     * @param count  每次请求从cache中批量获取keys的数量，count小于等于0时，默认为10，最大值为100
     * @return cache key值集合
     */
    Iterable<String> getKeys(String prefix, int count);

    /**
     * 设置指定key的数据存活时长（时间单位：毫秒）
     *
     * @param key        cache key值
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @return 当key值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expire(String key, long timeToLive);

    /**
     * 设置指定key的数据存活时长
     *
     * @param key        cache key值
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     * @return 当key值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expire(String key, long timeToLive, TimeUnit timeUnit);

    /**
     * 设置指定key的数据过期时间点
     *
     * @param key       cache key值
     * @param timestamp 数据的过期时间点
     * @return 当key值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expireAt(String key, long timestamp);

    /**
     * 清除指定key的过期时间信息
     *
     * @param key cache key值
     * @return 当key值所对应的数据存在，且过期时间清除成功时，返回true
     */
    boolean clearExpire(String key);

    /**
     * 获取指定key的ttl(秒)，
     * 返回值说明 -1: 未设置失效时间; -2: key不存在; 其他: 失效时间
     *
     * @param key cache key值
     * @return 指定cache key的ttl(秒)
     */
    long ttl(String key);

    /**
     * 验证指定的key值是否存在
     *
     * @param key cache key值
     * @return true: 存在, false: 不存在
     */
    boolean hasKey(String key);

    /**
     * 查询指定key的数据
     *
     * @param key cache key值
     * @return key所对应的数据
     */
    V get(String key);

    /**
     * 批量查询在指定keys数组中的数据，返回数据的顺序与keys的顺序是保持一致的
     * 特别是：如果keys中某些数据在cache中不存在，则在返回结果的相应位置会表示为一个null值
     *
     * @param keys cache key值
     * @return keys数组所对应的数据
     */
    List<?> batchGetAsList(List<String> keys);

    /**
     * 批量查询在指定keys数组中的数据，并返回一个Map
     *
     * @param keys cache key值
     * @return keys数组所对应的Map
     */
    Map<String, Object> batchGetAsMap(List<String> keys);

    /**
     * 查询指定的key是否保存了对应的null值，该方法不能用于验证指定的key是否存在<br/>
     * 该方法一般用于防止缓存穿透，所以不抛出cache相关的异常
     *
     * @param key cache key值
     * @return 如果指定的key保存了对应的null值，则返回true；否则返回false
     */
    boolean existsNull(String key);

    /**
     * 设置普通数据到cache中
     * 在后续不设置过期时间的情况下，该数据将永不过期
     *
     * @param key   cache key值
     * @param value 存储到cache中的数据
     */
    void set(String key, V value);

    /**
     * 批量设置普通数据到cache中
     * 在后续不设置过期时间的情况下，这些数据将永不过期
     *
     * @param values 待批量插入到数据库中的数据
     */
    void set(Map<String, V> values);

    /**
     * 设置普通数据到cache中
     * 带有指定的存活时间（时间单位：毫秒）
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     */
    void set(String key, V value, long timeToLive);

    /**
     * 设置普通数据到cache中
     * 带有指定的存活时间
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     */
    void set(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 批量设置普通数据到cache中
     * 带有指定的存活时间（时间单位：毫秒）
     *
     * @param values     待批量插入到数据库中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     */
    void set(Map<String, V> values, long timeToLive);

    /**
     * 批量设置普通数据到cache中
     * 带有指定的存活时间
     *
     * @param values     待批量插入到数据库中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     */
    void set(Map<String, V> values, long timeToLive, TimeUnit timeUnit);

    /**
     * 设置指定的key存放为null值，一般用于防止缓存穿透。不抛出cache操作相关的异常
     * 数据具有指定的存活时间（时间单位：毫秒）
     *
     * @param key        cache key值
     * @param timeToLive 数据存活时间
     */
    void setNull(String key, long timeToLive);

    /**
     * 设置指定的key存放为null值，一般用于防止缓存穿透。不抛出cache操作相关的异常
     *
     * @param key        cache key值
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     */
    void setNull(String key, long timeToLive, TimeUnit timeUnit);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 在后续不设置过期时间的情况下，该数据将永不过期
     *
     * @param key   cache key值
     * @param value 存储到cache中的数据
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    boolean trySet(String key, V value);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 在后续不设置过期时间的情况下，该数据将永不过期
     *
     * @param values 待批量插入到数据库中的数据
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    Map<String, Boolean> trySet(Map<String, V> values);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 带有指定的存活时间（时间单位：毫秒）
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    boolean trySet(String key, V value, long timeToLive);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 带有指定的存活时间
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @param timeUnit   数据存活时间timeToLive的时间单位
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 带有指定的存活时间（时间单位：毫秒）
     *
     * @param values     待批量插入到数据库中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    Map<String, Boolean> trySet(Map<String, V> values, long timeToLive);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 带有指定的存活时间
     *
     * @param values     待批量插入到数据库中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    Map<String, Boolean> trySet(Map<String, V> values, long timeToLive, TimeUnit timeUnit);

    /**
     * 删除指定key的数据
     *
     * @param keys cache key值
     * @return 返回成功删除的数量
     */
    long delete(String... keys);

    /**
     * 删除指定key的数据
     *
     * @param keys cache key集合
     * @return 返回成功删除的数量
     */
    long delete(Collection<String> keys);

    /**
     * 删除指定前缀key的数据
     *
     * @param prefix cache key值前缀
     * @return 实际删除的数量
     */
    long deleteByPrefix(String prefix);

    /**
     * 获取Map对象
     *
     * @param key cache key值
     * @return key所对应的Map
     */
    Map<String, V> getMap(String key);

    /**
     * 获取Map中指定的元素
     *
     * @param key        cache key值
     * @param elementKey 元素key值
     * @return 返回实际存储的元素
     */
    V getMapValue(String key, String elementKey);

    /**
     * 批量获取Map中指定的元素
     *
     * @param key         cache key值
     * @param elementKeys 元素key值集合
     * @return 返回实际存储的元素数据
     */
    Map<String, V> getAllMapValue(String key, Set<String> elementKeys);

    /**
     * 向Map中设置元素
     *
     * @param key        cache key值
     * @param elementKey 元素key值
     * @param value      新设置的元素
     * @return 如果在设置之前，已存在相关元素，则返回该元素；反之返回null
     */
    V putMapValue(String key, String elementKey, V value);

    /**
     * 如果元素不存在，则向Map中设置元素
     *
     * @param key        cache key值
     * @param elementKey 元素key值
     * @param value      新设置的元素
     * @return 如果元素不存在，则返回null；否则返回已存在的元素
     */
    V putMapValueIfAbsent(String key, String elementKey, V value);

    /**
     * 向Map中批量设置元素
     *
     * @param key cache key值
     * @param map 待设置的元素集合
     */
    void putAllMapValue(String key, Map<String, ? extends V> map);

    /**
     * 将cache map中的数据copy到另外一个map结构中
     * 1、如果fromKey不存在，则返回false
     * 2、如果fromKey不是map结构，则抛出cache的操作异常
     * 3、如果toKey已存在，但不是map结构，则抛出cache的操作异常
     *
     * @param fromKey       待复制的cache map的key值
     * @param toKey         复制目的地cache map的key值
     * @param deleteFromKey 复制之后是否删除fromKey值
     * @return 操作结果
     */
    boolean copyOfMap(String fromKey, String toKey, boolean deleteFromKey);

    /**
     * 删除Map中指定的元素
     *
     * @param key        cache key值
     * @param elementKey 元素key值
     * @return 如果元素存在，则返回该元素；否则返回null
     */
    V removeMapValue(String key, String elementKey);

    /**
     * 删除Map中所有的元素
     *
     * @param key cache key值
     * @return 返回是否删除成功
     */
    boolean removeAllMapValue(String key);

    /**
     * 批量删除Map中指定的元素
     *
     * @param key         cache key值
     * @param elementKeys 元素key值集合
     * @return 返回删除成功的elementsKey的数量
     */
    long removeAllMapValue(String key, Collection<String> elementKeys);

    /**
     * 获取List对象
     *
     * @param key cache key值
     * @return key所对应的List
     */
    List<V> getList(String key);

    /**
     * 获取List中指定位置的元素
     *
     * @param key   cache key值
     * @param index 指定的位置
     * @return key所对应的List指定位置的元素
     */
    V getListValue(String key, int index);

    /**
     * 获取list中下标[0, toIndex]之间的元素，闭区间
     *
     * @param key     cache key元素
     * @param toIndex 截止的下标
     * @return 指定范围内的元素
     */
    List<V> getListValueByRange(String key, int toIndex);

    /**
     * 获取list中下标[fromIndex, toIndex]之间的元素，闭区间
     *
     * @param key       cache key元素
     * @param fromIndex 开始的下标
     * @param toIndex   截止的下标
     * @return 指定范围内的元素
     */
    List<V> getListValueByRange(String key, int fromIndex, int toIndex);

    /**
     * 向List中插入元素
     *
     * @param key   cache key值
     * @param value 新插入的元素
     */
    void addListValue(String key, V value);

    /**
     * 向List中批量插入元素
     *
     * @param key    cache key值
     * @param values 新插入的元素集合
     */
    void addAllListValue(String key, Collection<? extends V> values);

    /**
     * 向List中的指定位置批量插入元素
     *
     * @param key    cache key值
     * @param index  指定的位置
     * @param values 新插入的元素集合
     */
    void addAllListValue(String key, int index, Collection<? extends V> values);

    /**
     * 对指定的List进行修剪，让该List只保留[fromIndex, toIndex]区间内的元素
     *
     * @param key       cache key值
     * @param fromIndex 开始的下标
     * @param toIndex   截止的下标
     */
    void trimList(String key, int fromIndex, int toIndex);

    /**
     * 删除List中指定的元素
     *
     * @param key   cache key值
     * @param value 待删除的元素
     * @return 是否删除成功
     */
    boolean removeListValue(String key, V value);

    /**
     * 删除List中指定的元素集合
     *
     * @param key    cache key值
     * @param values 待删除的元素集合
     * @return 是否删除成功
     */
    boolean removeAllListValue(String key, Collection<? extends V> values);

    /**
     * 获取cache Set对象
     *
     * @param key cache key值
     * @return key所对应的Set
     */
    Set<V> getSet(String key);

    /**
     * 验证指定的数据是否在cache Set中存在
     *
     * @param key   cache key值
     * @param value 待验证的数据
     * @return 如果存在，则返回true；反之返回false
     */
    boolean containsSetValue(String key, V value);

    /**
     * 验证指定的数据集合是否在cache Set中存在
     *
     * @param key    cache key值
     * @param values 待验证的数据
     * @return 如果全部存在，则返回true；反之返回false
     */
    boolean containsAllSetValue(String key, Collection<? extends V> values);

    /**
     * 获取cache Set的Iterator对象
     *
     * @param key cache key值
     * @return key所对应的Iterator
     */
    Iterator<V> iteratorSet(String key);

    /**
     * 向cache Set插入指定的数据
     *
     * @param key   cache key值
     * @param value 待插入的数据
     * @return 插入成功返回true；否则返回false
     */
    boolean addSetValue(String key, V value);

    /**
     * 向cache Set中批量插入数据
     *
     * @param key    cache key值
     * @param values 待插入的数据集合
     * @return 如果插入成功，则返回true；否则返回false
     */
    boolean addAllSetValue(String key, Collection<? extends V> values);

    /**
     * 随机在Set中删除一个元素
     *
     * @param key cache key值
     * @return 已删除的元素
     */
    V removeSetRandom(String key);

    /**
     * 随机在Set中删除指定数量的元素
     *
     * @param key   cache key值
     * @param count 删除数据的数量
     * @return 已删除的元素集合
     */
    Set<V> removeSetRandom(String key, int count);

    /**
     * 删除Set中指定的元素
     *
     * @param key   cache key值
     * @param value 待删除的元素
     * @return 指定的value存在，且删除成功时，返回true；否则返回false
     */
    boolean removeSetValue(String key, V value);

    /**
     * 删除Set中指定的元素集合
     *
     * @param key    cache key值
     * @param values 待删除的元素集合
     * @return 是否删除成功
     */
    boolean removeAllSetValue(String key, Collection<? extends V> values);

    /**
     * 获取cache 顺序的set对象
     *
     * @param key cache key值
     * @return key所对应的Set
     */
    Set<V> getOrderSet(String key);

    /**
     * 验证指定的数据是否在cache set中存在
     *
     * @param key   cache key值
     * @param value 待验证的数据
     * @return 如果存在，则返回true；反之返回false
     */
    boolean containsOrderSetValue(String key, V value);

    /**
     * 验证指定的数据集合是否在cache set中存在
     *
     * @param key    cache key值
     * @param values 待验证的数据
     * @param <V>
     * @return 如果全部存在，则返回true；反之返回false
     */
    boolean containsAllOrderSetValue(String key, Collection<? extends V> values);

    /**
     * 获取cache set的Iterator对象
     *
     * @param key cache key值
     * @return key所对应的Iterator
     */
    Iterator<V> iteratorOrderSet(String key);

    /**
     * 向cache set插入指定的数据
     *
     * @param key   cache key值
     * @param value 待插入的数据
     * @return 插入成功返回true；否则返回false
     */
    boolean addOrderSetValue(String key, V value);

    /**
     * 向cache set中批量插入数据
     *
     * @param key    cache key值
     * @param values 待插入的数据集合
     * @return 如果插入成功，则返回true；否则返回false
     */
    boolean addAllOrderSetValue(String key, Collection<? extends V> values);

    /**
     * 删除set中指定的元素
     *
     * @param key   cache key值
     * @param value 待删除的元素
     * @return 指定的value存在，且删除成功时，返回true；否则返回false
     */
    boolean removeOrderSetValue(String key, V value);

    /**
     * 删除set中指定的元素集合
     *
     * @param key    cache key值
     * @param values 待删除的元素集合
     * @return 是否删除成功
     */
    boolean removeAllOrderSetValue(String key, Collection<? extends V> values);

    /**
     * 计算指定的cache zset大小
     *
     * @param key cache key值
     * @return 指定key值的cache zset大小
     */
    int countOfZSet(String key);

    /**
     * 计算指定的cache zset从startScore到endScore之间的数据个数<br/>
     * startScoreInclusive和endScoreInclusive分别用于指定是否包含初始和结束的score值
     *
     * @param key                 cache key值
     * @param startScore          开始位置的score值
     * @param startScoreInclusive 是否包含开始位置的score值
     * @param endScore            结束位置的score值
     * @param endScoreInclusive   是否包含结束位置的score值
     * @return 满足条件的元素个数
     */
    int countOfZSet(String key, double startScore, boolean startScoreInclusive, double endScore,
                    boolean endScoreInclusive);

    /**
     * 获取指定zset元素所对应的score值
     *
     * @param key     cache key值
     * @param element zset中指定的数据
     * @return 指定元素所对应的score值，如果元素不存在，则返回null
     */
    Double getZSetScore(String key, V element);

    /**
     * 获取指定的cache zset迭代器
     *
     * @param key cache key值
     * @return 指定的cache zset迭代器
     */
    Iterator<V> iteratorZSet(String key);

    /**
     * 获取指定的cache zset迭代器，迭代器中的结果满足范式pattern
     *
     * @param key     cache key值
     * @param pattern 结果的范式
     * @return 指定的cache zset迭代器
     */
    Iterator<V> iteratorZSet(String key, String pattern);

    /**
     * 按顺序/倒序获取指定的cache zset中，从fromIndex到toIndex的数据元素集合(仅包括数据)<br/>
     * cache下标从0开始
     *
     * @param key       cache key值
     * @param fromIndex 开始位置，包含
     * @param toIndex   结束位置，包括
     * @param reverse   是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
     * @return 在指定范围内的cache zset数据元素集合
     */
    Collection<V> getZSetValueRange(String key, int fromIndex, int toIndex, boolean reverse);

    /**
     * 按顺序/倒序获取指定cache zset中，分数值从startScore到endScore之间的数据元素集合(仅包括数据)<br/>
     * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
     *
     * @param key                 cache key值
     * @param startScore          开始位置的score值
     * @param startScoreInclusive 是否包含开始位置的score值
     * @param endScore            结束位置的score值
     * @param endScoreInclusive   是否包含结束位置的score值
     * @param reverse             是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
     * @return 在指定范围内的cache zset数据元素集合
     */
    Collection<V> getZSetValueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
                                        boolean endScoreInclusive, boolean reverse);

    /**
     * 按顺序/倒序获取指定cache zset中，分数值从startScore到endScore之间的数据元素集合(仅包括数据)，返回结果中指定offset和limit<br/>
     * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
     *
     * @param key                 cache key值
     * @param startScore          开始位置的score值
     * @param startScoreInclusive 是否包含开始位置的score值
     * @param endScore            结束位置的score值
     * @param endScoreInclusive   是否包含结束位置的score值
     * @param offset              数据起始位置的偏移量
     * @param limit               返回数据元素集合最大的大小
     * @param reverse             是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
     * @return 在指定范围内的cache zset数据元素集合
     */
    Collection<V> getZSetValueRange(String key, double startScore, boolean startScoreInclusive, double endScore,
                                        boolean endScoreInclusive, int offset, int limit, boolean reverse);

    /**
     * 按顺序/倒序获取指定的cache zset中，从fromIndex到toIndex的实体元素集合(包括数据和分值)<br/>
     * cache下标从0开始
     *
     * @param key       cache key值
     * @param fromIndex 开始位置，包含
     * @param toIndex   结束位置，包括
     * @param reverse   是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
     * @return 在指定范围内的cache zset实体元素集合
     */
    Collection<DistributedScoredEntry<V>> getZSetEntryRange(String key, int fromIndex, int toIndex, boolean reverse);

    /**
     * 按顺序/倒序获取指定cache zset中，分数值从startScore到endScore之间的实体元素集合(包括数据和分值)<br/>
     * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
     *
     * @param key                 cache key值
     * @param startScore          开始位置的score值
     * @param startScoreInclusive 是否包含开始位置的score值
     * @param endScore            结束位置的score值
     * @param endScoreInclusive   是否包含结束位置的score值
     * @param reverse             是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
     * @return 在指定范围内的cache zset实体元素集合
     */
    Collection<DistributedScoredEntry<V>> getZSetEntryRange(String key, double startScore, boolean startScoreInclusive,
                                                                double endScore, boolean endScoreInclusive, boolean reverse);

    /**
     * 按顺序/倒序获取指定cache zset中，分数值从startScore到endScore之间的实体元素集合(包括数据和分值)，返回结果中指定offset和limit<br/>
     * startScoreInclusive和endScoreInclusive分别用于指定：返回结果中是否包含初始和结束的score值
     *
     * @param key                 cache key值
     * @param startScore          开始位置的score值
     * @param startScoreInclusive 是否包含开始位置的score值
     * @param endScore            结束位置的score值
     * @param endScoreInclusive   是否包含结束位置的score值
     * @param offset              数据起始位置的偏移量
     * @param limit               返回数据元素集合最大的大小
     * @param reverse             是否倒序排序，默认为顺序排序，即从小到大，如果reverse值为true时，将按从大到小的顺序
     * @return 在指定范围内的cache zset实体元素集合
     */
    Collection<DistributedScoredEntry<V>> getZSetEntryRange(String key, double startScore, boolean startScoreInclusive, double endScore,
                                                                boolean endScoreInclusive, int offset, int limit, boolean reverse);

    /**
     * 向zset中插入元素，如果元素element已存在，则更新其score值
     *
     * @param key     cache key值
     * @param element 待插入的数据
     * @param score   待插入的数据所对应的分值
     * @return 是否插入成功
     */
    boolean addZSetValue(String key, V element, double score);

    /**
     * 向zset中批量插入元素，如果元素已存在，则更新其score值
     *
     * @param key      cache key值
     * @param elements 待插入的元素集合
     * @return 成功新插入的数量（不包括已存在的元素）
     */
    int addAllZSetValue(String key, Map<V, Double> elements);

    /**
     * 给相应的zset元素中的分值加上delta
     *
     * @param key     cache key值
     * @param element zset中的元素
     * @param delta   待加上分值上的变量
     * @return 更新后的分值
     */
    Double addZSetScore(String key, V element, double delta);

    /**
     * 删除zset中指定的元素
     *
     * @param key     cache key值
     * @param element 待删除的元素
     * @return 是否删除成功
     */
    boolean removeZSetValue(String key, V element);

    /**
     * 批量删除zset中指定的元素
     *
     * @param key    cache key值
     * @param values 待删除的元素集合
     * @return 是否删除成功
     */
    boolean removeAllZSetValue(String key, Collection<V> values);

    /**
     * 删除指定范围内的zset中的元素
     *
     * @param key       cache key
     * @param fromIndex 开始序号，包含
     * @param toIndex   结束序号，包含
     * @return 删除数量
     */
    int removeZSetValueByRank(String key, int fromIndex, int toIndex);

    /**
     * 删除指定score值范围内的zeset中的元素
     *
     * @param key                 cache key值
     * @param startScore          开始位置的score值
     * @param startScoreInclusive 是否包含开始位置的score值
     * @param endScore            结束位置的score值
     * @param endScoreInclusive   是否包含结束位置的score值
     * @return 删除数量
     */
    int removeZSetValueByScore(String key, double startScore, boolean startScoreInclusive, double endScore,
                               boolean endScoreInclusive);


    /**
     * 获取atomic值，如果数据不存在，则返回0
     *
     * @param key cache key值
     * @return atomic值
     */
    long atomicGet(String key);

    /**
     * 设置atomic值
     *
     * @param key   cache key值
     * @param value 新设定的值
     */
    void atomicSet(String key, long value);

    /**
     * 先获取当前atomic值，然后加上指定的delta数值
     *
     * @param key   cache key值
     * @param delta 加到atomic上的值
     * @return 变化之前的atomic值
     */
    long atomicGetAndAdd(String key, long delta);

    /**
     * 先加上指定的delta数值，然后再获取add delta后的atomic值
     *
     * @param key   cache key值
     * @param delta 加到atomic上的值
     * @return 变化之前的atomic值
     */
    long atomicAddAndGet(String key, long delta);

    /**
     * 获取当前值，然后设置为另外一个数值
     *
     * @param key   cache key值
     * @param value 新设定的值
     * @return 变化之前的atomic值
     */
    long atomicGetAndSet(String key, long value);

    /**
     * 获取当前值，然后删除
     *
     * @param key cache key值
     * @return key所对应的数据
     */
    long atomicGetAndDelete(String key);

    /**
     * 获取当前值，然后递增1
     *
     * @param key cache key值
     * @return 变化之前的atomic值
     */
    long getAndIncrement(String key);

    /**
     * 先递增1，然后再返回递增后的新值
     *
     * @param key cache key值
     * @return 变化之后的atomic值
     */
    long incrementAndGet(String key);

    /**
     * 获取当前值，然后递减1
     *
     * @param key cache key值
     * @return 变化之前的atomic值
     */
    long getAndDecrement(String key);

    /**
     * 先递减1，然后再返回递减后的新值
     *
     * @param key cache key值
     * @return 变化之后的atomic值
     */
    long decrementAndGet(String key);

    /**
     * Atomically sets the value to the given updated value
     * only if the current value {@code ==} the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful; or false if the actual value
     *         was not equal to the expected value.
     */
    boolean compareAndSet(String key, long expect, long update);

    /**
     * 返回锁对象
     *
     * @param key cache key值
     * @return 锁对象
     */
    DistributedLock getLock(String key);

    /**
     * 返回读写锁对象
     *
     * @param key cache key值
     * @return 锁对象
     */
    DistributedReadWriteLock getReadWriteLock(String key);

    /**
     * 获取CountDownLatch闭锁对象
     *
     * @param key cache key值
     * @return 闭锁
     */
    DistributedCountDownLatch getCountDownLatch(String key);
}
