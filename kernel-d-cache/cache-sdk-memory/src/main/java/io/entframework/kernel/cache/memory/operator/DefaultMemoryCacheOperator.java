/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.cache.memory.operator;

import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.cache.api.constants.CacheConstants;
import io.entframework.kernel.cache.memory.AbstractMemoryCacheOperator;

/**
 * 默认内存缓存的实现，value存放Object类型
 *
 * @date 2021/2/24 22:16
 */
public class DefaultMemoryCacheOperator<T> extends AbstractMemoryCacheOperator<T> {

    public DefaultMemoryCacheOperator(TimedCache<String, T> timedCache) {
        super(timedCache, CacheConstants.DEFAULT_OBJECT_CACHE_PREFIX);
    }

    public DefaultMemoryCacheOperator(TimedCache<String, T> timedCache, String prefix) {
        super(timedCache, prefix);
    }

}
