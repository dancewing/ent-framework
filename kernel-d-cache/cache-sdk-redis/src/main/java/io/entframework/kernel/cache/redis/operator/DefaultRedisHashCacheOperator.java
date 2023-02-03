/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.cache.redis.operator;

import io.entframework.kernel.cache.api.CacheManager;
import io.entframework.kernel.cache.redis.AbstractRedisHashCacheOperator;

/**
 * 默认redis缓存的实现，value存放String类型
 *
 * @date 2021/2/24 22:16
 */
public class DefaultRedisHashCacheOperator<T> extends AbstractRedisHashCacheOperator<T> {

    public DefaultRedisHashCacheOperator(CacheManager<T> cacheManager) {
        super(cacheManager);
    }

}
