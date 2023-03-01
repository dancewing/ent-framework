/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api;

import io.entframework.kernel.auth.api.pojo.auth.LoginRequest;
import io.entframework.kernel.auth.api.pojo.auth.LoginResponse;
import io.entframework.kernel.auth.api.pojo.auth.LoginWithTokenRequest;

/**
 * 认证服务的接口，包括基本的登录退出操作和校验token等操作
 *
 * @date 2020/10/26 14:41
 */
public interface AuthServiceApi {

    /**
     * 常规登录操作
     * @param loginRequest 登录的请求
     * @return token 一般为jwt token
     * @date 2020/10/26 14:41
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 登录（直接用账号登录），一般用在第三方登录
     * @param username 账号
     * @date 2020/10/26 14:40
     */
    LoginResponse loginWithUserName(String username);

    /**
     * 登录（通过账号和sso后的token），一般用在单点登录
     * @param username 账号
     * @param caToken sso登录成功后的会话
     * @date 2021/5/25 22:44
     */
    LoginResponse loginWithUserNameAndCaToken(String username, String caToken);

    /**
     * 通过token进行登录，一般用在单点登录服务
     * @param loginWithTokenRequest 请求
     * @date 2021/5/25 22:44
     */
    LoginResponse loginWithToken(LoginWithTokenRequest loginWithTokenRequest);

    /**
     * 当前登录人退出登录
     *
     * @date 2020/10/19 14:16
     */
    void logout();

    /**
     * 移除某个token，也就是退出某个用户
     * @param token 某个用户的登录token
     * @date 2020/10/19 14:16
     */
    void logoutWithToken(String token);

    /**
     * 取消冻结帐号
     *
     * @date 2022/1/22 16:37
     */
    void cancelFreeze(LoginRequest loginRequest);

}
