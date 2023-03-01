/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.session.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.config.AuthConfigProperties;
import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.auth.api.constants.LoginCacheConstants;
import io.entframework.kernel.auth.api.cookie.SessionCookieCreator;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.auth.session.cookie.DefaultSessionCookieCreator;
import io.entframework.kernel.auth.session.impl.DefaultSessionManager;
import io.entframework.kernel.auth.session.impl.LoginUserImpl;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.cache.api.constants.CacheConstants;
import io.entframework.kernel.cache.memory.operator.DefaultMemoryCacheOperator;
import io.entframework.kernel.jwt.api.config.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Session,Cache 模块的自动配置
 *
 * @author jeff_qian
 * @date 2020/11/30 22:16
 */
@Configuration
@EnableConfigurationProperties({ AuthConfigProperties.class })
public class KernelAuthSessionAutoConfiguration {

    private final AuthConfigProperties authConfigProperties;

    public KernelAuthSessionAutoConfiguration(AuthConfigProperties authConfigProperties) {
        this.authConfigProperties = authConfigProperties;
    }

    /**
     * session cookie的创建
     *
     * @date 2020/12/27 15:48
     */
    @Bean
    @ConditionalOnMissingBean(SessionCookieCreator.class)
    public SessionCookieCreator sessionCookieCreator() {
        return new DefaultSessionCookieCreator();
    }

    /**
     * 登录用户的缓存，默认使用内存方式
     * <p>
     * 如需redis，可在项目创建一个名为 loginUserCache 的bean替代即可
     *
     * @date 2021/1/31 21:04
     */
    @Bean
    @ConditionalOnMissingBean(name = LoginCacheConstants.LOGIN_USER_CACHE_BEAN_NAME)
    public CacheOperatorApi<LoginUser> loginUserCache() {
        long sessionExpiredSeconds = authConfigProperties.getSessionExpiredSeconds();
        TimedCache<String, LoginUser> loginUsers = CacheUtil.newTimedCache(1000L * sessionExpiredSeconds);
        return new DefaultMemoryCacheOperator<>(loginUsers, AuthConstants.LOGGED_TOKEN_PREFIX);
    }

    /**
     * 登录用户token的缓存，默认使用内存方式
     * <p>
     * 如需redis，可在项目创建一个名为 allPlaceLoginTokenCache 的bean替代即可
     *
     * @date 2021/1/31 21:04
     */
    @Bean
    @ConditionalOnMissingBean(name = LoginCacheConstants.ALL_USER_LOGIN_TOKEN_CACHE_BEAN_NAME)
    public CacheOperatorApi<Set<String>> allUserLoginTokenCache() {
        TimedCache<String, Set<String>> loginTokens = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        return new DefaultMemoryCacheOperator<>(loginTokens, AuthConstants.LOGGED_USERID_PREFIX);
    }

    /**
     * 默认的session缓存为内存缓存，方便启动
     * <p>
     * 如需替换请在项目中增加一个SessionManagerApi Bean即可
     *
     * @date 2020/11/30 22:17
     */
    @Bean
    @ConditionalOnMissingBean(SessionManagerApi.class)
    public SessionManagerApi sessionManagerApi(CacheOperatorApi<LoginUser> loginUserCache,
            CacheOperatorApi<Set<String>> allUserLoginTokenCache, JwtProperties jwtProperties) {
        Long sessionExpiredSeconds = jwtProperties.getExpiredSeconds();
        return new DefaultSessionManager(loginUserCache, allUserLoginTokenCache, sessionExpiredSeconds,
                sessionCookieCreator(), this.authConfigProperties);
    }

    @Bean
    @ConditionalOnMissingBean(LoginUserApi.class)
    public LoginUserApi loginUserApi() {
        return new LoginUserImpl();
    }

}
