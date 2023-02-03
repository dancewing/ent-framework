/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.cache.redis.operator;

import io.entframework.kernel.cache.api.CacheManager;
import io.entframework.kernel.cache.redis.AbstractRedisCacheOperator;

/**
 * 默认redis缓存的实现，value存放Object类型
 *
 * @date 2021/2/24 22:16
 */
public class DefaultRedisCacheOperator<T> extends AbstractRedisCacheOperator<T> {

    public DefaultRedisCacheOperator(CacheManager<T> cacheManager) {
        super(cacheManager);
    }


}
