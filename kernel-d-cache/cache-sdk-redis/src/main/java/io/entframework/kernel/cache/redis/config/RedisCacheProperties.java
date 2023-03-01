/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.cache.redis.config;

import lombok.Data;

/**
 * @author jeff_qian
 */
@Data
public class RedisCacheProperties {

    /**
     * Redis host
     */
    private String host;

    private Integer port;

    private String auth;

    private Integer database;

    private Boolean ssl;

    private Integer connectionPoolSize;

    private Integer connectionMinimumIdleSize;

    private Integer idleConnectionTimeout;

    /**
     * redis集群存储节点数量
     */
    private Integer clusterNodeCount;

}
