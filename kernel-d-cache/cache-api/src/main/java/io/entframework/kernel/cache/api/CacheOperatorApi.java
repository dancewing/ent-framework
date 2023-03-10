/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.cache.api;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 缓存操作的基础接口，可以实现不同种缓存实现
 * <p>
 * 泛型为cache的值类class类型
 *
 * @date 2020/7/8 22:02
 */
public interface CacheOperatorApi<T> {

    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     * @date 2020/7/8 22:06
     */
    void put(String key, T value);

    /**
     * 添加缓存（带过期时间，单位是秒）
     * @param key 键
     * @param value 值
     * @param timeoutSeconds 过期时间，单位秒
     * @date 2020/7/8 22:07
     */
    void put(String key, T value, Long timeoutSeconds);

    /**
     * 通过缓存key获取缓存
     * @param key 键
     * @return 值
     * @date 2020/7/8 22:08
     */
    T get(String key);

    /**
     * 删除缓存
     * @param key 键，多个
     * @date 2020/7/8 22:09
     */
    void remove(String... key);

    /**
     * 删除缓存
     * @param key 键，多个
     * @date 2020/7/8 22:09
     */
    void expire(String key, Long expiredSeconds);

    /**
     * 判断某个key值是否存在于缓存
     * @param key 缓存的键
     * @return true-存在，false-不存在
     * @date 2020/11/20 16:50
     */
    boolean contains(String key);

    /**
     * 获得缓存的所有key列表（不带common prefix的）
     * @return key列表
     * @date 2020/7/8 22:11
     */
    Collection<String> getAllKeys();

    /**
     * 获得缓存的所有值列表
     * @return 值列表
     * @date 2020/7/8 22:11
     */
    Collection<T> getAllValues();

    /**
     * 获取所有的key，value
     * @return 键值map
     * @date 2020/7/8 22:11
     */
    Map<String, T> getAllKeyValues();

    /**
     * 通用缓存的前缀，用于区分不同业务
     * <p>
     * 如果带了前缀，所有的缓存在添加的时候，key都会带上这个前缀
     * @return 缓存前缀
     * @date 2020/7/9 11:06
     */
    String getPrefix();

    /**
     * 计算最终插入缓存的key值
     * <p>
     * key的组成： 缓存前缀 + keyParam.toUpperCase
     * @param keyParam 用户传递的key参数
     * @return 最终插入缓存的key值
     * @date 2021/7/30 21:18
     */
    default String calcKey(String keyParam) {
        if (CharSequenceUtil.isBlank(keyParam)) {
            return getPrefix();
        }
        else {
            return getPrefix() + keyParam.toUpperCase();
        }
    }

}
