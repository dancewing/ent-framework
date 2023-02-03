/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.authentication.config;


import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.auth.api.constants.LoginCacheConstants;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.cache.memory.operator.DefaultMemoryCacheOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录缓存的自动配置
 *
 * @author jeff_qian
 * @date 2022/1/22 17:40
 */
@Configuration
public class KernelLoginCacheAutoConfiguration {

    /**
     * 登录帐号冻结的缓存
     *
     * @date 2022/1/22 17:45
     */
    @Bean
    @ConditionalOnMissingBean(name = "loginCacheOperatorApi")
    public CacheOperatorApi<String> loginCacheOperatorApi() {
        TimedCache<String, String> loginTimeCache = CacheUtil.newTimedCache(LoginCacheConstants.LOGIN_CACHE_TIMEOUT_SECONDS * 1000);
        return new DefaultMemoryCacheOperator<>(loginTimeCache, LoginCacheConstants.LOGIN_CACHE_PREFIX);
    }
}
