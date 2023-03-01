/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.cache.redis.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedisCacheNode extends RedisCacheProperties {

    private String alias;

    private String prefix;

    private Boolean isHash;

}
