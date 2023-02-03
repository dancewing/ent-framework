/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.cache.redis.util;

import io.entframework.kernel.cache.api.model.DistributedScoredEntry;
import org.redisson.client.protocol.ScoredEntry;

/**
 * 数据转换器
 *
 */
public class DataConverter {
    /**
     * 将redisson的ScoredEntry转换为自定义的DistributedScoredEntry
     * 对外不暴露redisson的ScoredEntry
     *
     * @param entry 实体信息
     * @param <V>
     * @return 自定义的DistributedScoredEntry
     */
    public static <V> DistributedScoredEntry<V> converter(ScoredEntry<V> entry) {
        return new DistributedScoredEntry<>(entry.getScore(), entry.getValue());
    }
}
