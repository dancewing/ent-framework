/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.cache.redis.starter;

import io.entframework.kernel.cache.redis.config.KernelRedisCacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基于redis缓存的默认配置，默认提供两个RedisTemplate工具类，其他的各个模块自行配置
 *
 * @date 2021/1/31 20:33
 */
@Configuration
@EnableConfigurationProperties(KernelRedisCacheProperties.class)
public class KernelRedisCacheAutoConfiguration {

}
