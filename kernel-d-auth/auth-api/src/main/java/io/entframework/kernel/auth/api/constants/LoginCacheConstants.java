/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.api.constants;

/**
 * * 登录前缀相关的常量
 *
 * @author jeff_qian
 */
public interface LoginCacheConstants {

    /**
     * 登录最大次数
     */
    Integer MAX_LOGIN_COUNT = 5;

    /**
     * 登录冻结缓存前缀
     */
    String LOGIN_CACHE_PREFIX = "login:";

    /**
     * 冻结时间
     */
    Long LOGIN_CACHE_TIMEOUT_SECONDS = 1800L;

    /**
     * 登录用户的缓存
     */
    String LOGIN_USER_CACHE_BEAN_NAME = "loginUserCache";

    /**
     * 登录用户所有token的缓存
     */
    String ALL_USER_LOGIN_TOKEN_CACHE_BEAN_NAME = "allUserLoginTokenCache";

}
