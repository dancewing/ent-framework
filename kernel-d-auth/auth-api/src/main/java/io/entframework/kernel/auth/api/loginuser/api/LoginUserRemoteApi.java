/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.loginuser.api;

import io.entframework.kernel.auth.api.loginuser.pojo.LoginUserRequest;
import io.entframework.kernel.auth.api.loginuser.pojo.SessionValidateResponse;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取当前登录用户的远程调用方法，供微服务使用
 *
 * @date 2021/9/29 10:08
 */
public interface LoginUserRemoteApi {

    /**
     * 通过token获取登录的用户
     *
     * @date 2021/9/29 10:08
     */
    @RequestMapping(value = "/login-user-remote/get-login-user-by-token", method = RequestMethod.POST)
    LoginUser getLoginUserByToken(@RequestBody LoginUserRequest loginUserRequest);

    /**
     * 判断token是否存在会话
     *
     * @date 2021/9/29 11:39
     */
    @RequestMapping(value = "/login-user-remote/have-session", method = RequestMethod.GET)
    SessionValidateResponse haveSession(@RequestParam("token") String token);

    /**
     * 通过loginUser获取刷新后的LoginUser对象
     *
     * @date 2021/9/29 11:39
     */
    @RequestMapping(value = "/login-user-remote/get-effective-login-user", method = RequestMethod.POST)
    LoginUser getEffectiveLoginUser(@RequestBody LoginUser loginUser);

}
