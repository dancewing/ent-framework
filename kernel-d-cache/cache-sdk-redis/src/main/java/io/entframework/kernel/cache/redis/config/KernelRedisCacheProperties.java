/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.cache.redis.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@ConfigurationProperties("kernel.cache.redis")
@Data
@EqualsAndHashCode(callSuper = true)
public class KernelRedisCacheProperties extends RedisCacheProperties {
    private List<RedisCacheNode> nodes = new ArrayList<>();
}
