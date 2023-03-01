package io.entframework.kernel.cache.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * cache有序集合的实体数据
 */
@Data
@AllArgsConstructor
public class DistributedScoredEntry<V> {

    /**
     * 分值
     */
    private Double score;

    /**
     * 数值
     */
    private V value;

}
