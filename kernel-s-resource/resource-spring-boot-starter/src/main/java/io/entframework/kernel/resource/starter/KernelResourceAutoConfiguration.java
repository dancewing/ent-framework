/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.resource.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.cache.api.constants.CacheConstants;
import io.entframework.kernel.cache.memory.operator.DefaultMemoryCacheOperator;
import io.entframework.kernel.resource.api.constants.ResourceConstants;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 资源业务的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 21:54
 */
@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.resource" })
public class KernelResourceAutoConfiguration {

    /**
     * 资源缓存
     *
     * @date 2021/5/17 16:44
     */
    @Bean
    @ConditionalOnMissingBean(name = ResourceConstants.RESOURCE_CACHE_BEAN_NAME)
    public CacheOperatorApi<ResourceDefinition> resourceCache() {
        TimedCache<String, ResourceDefinition> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        return new DefaultMemoryCacheOperator<>(timedCache, ScannerConstants.RESOURCE_CACHE_KEY);
    }

    @Bean
    public KernelResourceModuleRegister kernelResourceModuleRegister() {
        return new KernelResourceModuleRegister();
    }

}
