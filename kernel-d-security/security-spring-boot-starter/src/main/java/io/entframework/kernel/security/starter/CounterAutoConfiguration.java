/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.cache.api.constants.CacheConstants;
import io.entframework.kernel.cache.memory.operator.DefaultMemoryCacheOperator;
import io.entframework.kernel.security.api.BlackListApi;
import io.entframework.kernel.security.api.CountValidatorApi;
import io.entframework.kernel.security.api.WhiteListApi;
import io.entframework.kernel.security.api.constants.CounterConstants;
import io.entframework.kernel.security.blackwhite.BlackListService;
import io.entframework.kernel.security.blackwhite.WhiteListService;
import io.entframework.kernel.security.count.DefaultCountValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 计数器和黑白名单自动配置
 *
 * @date 2020/12/1 21:44
 */
@Configuration
public class CounterAutoConfiguration {

    /**
     * 黑名单校验
     *
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(BlackListApi.class)
    public BlackListApi blackListApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        DefaultMemoryCacheOperator<String> blackListMemoryCache = new DefaultMemoryCacheOperator<>(timedCache, CounterConstants.BLACK_LIST_CACHE_KEY_PREFIX);
        return new BlackListService(blackListMemoryCache);
    }

    /**
     * 计数校验器
     *
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(CountValidatorApi.class)
    public CountValidatorApi countValidatorApi() {
        TimedCache<String, Long> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        DefaultMemoryCacheOperator<Long> defaultCountValidateCache = new DefaultMemoryCacheOperator<>(timedCache, CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX);
        return new DefaultCountValidator(defaultCountValidateCache);
    }

    /**
     * 白名单校验
     *
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(WhiteListApi.class)
    public WhiteListApi whiteListApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        DefaultMemoryCacheOperator<String> whiteListMemoryCache = new DefaultMemoryCacheOperator(timedCache, CounterConstants.WHITE_LIST_CACHE_KEY_PREFIX);
        return new WhiteListService(whiteListMemoryCache);
    }

}
