/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.auth.api.AuthServiceApi;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.auth.LoginRequest;
import io.entframework.kernel.auth.api.pojo.auth.LoginResponse;
import io.entframework.kernel.auth.api.pojo.auth.LoginWithTokenRequest;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.login.CurrentUserInfoResponse;
import io.entframework.kernel.system.api.pojo.login.ValidateTokenRequest;
import io.entframework.kernel.system.modular.factory.UserLoginInfoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import jakarta.validation.Valid;

import java.util.Set;

/**
 * 登录登出控制器
 *
 * @date 2021/3/17 17:23
 */
@RestController
@Slf4j
@ApiResource(name = "登陆登出管理")
public class LoginController {

    @Resource
    private AuthServiceApi authServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    /**
     * 用户登陆
     *
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆", path = "/login", requiredLogin = false, requiredPermission = false)
    public ResponseData<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return ResponseData.ok(loginResponse);
    }

    /**
     * 基于token登录，适用于单点登录，将caToken请求过来，进行解析，并创建本系统可以识别的token
     *
     * @date 2021/5/25 22:36
     */
    @PostResource(name = "适用于单点登录", path = "/login-with-token", requiredLogin = false, requiredPermission = false)
    public ResponseData<String> loginWithToken(@RequestBody @Validated LoginWithTokenRequest loginWithTokenRequest) {
        LoginResponse loginResponse = authServiceApi.loginWithToken(loginWithTokenRequest);
        return ResponseData.ok(loginResponse.getToken());
    }

    /**
     * 用户登出
     *
     * @date 2021/3/17 17:24
     */
    @ApiResource(name = "登出", path = "/logout", requiredPermission = false, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData<Void> logoutAction() {
        authServiceApi.logout();
        return ResponseData.OK_VOID;
    }

    /**
     * 获取当前用户的用户信息
     *
     * @date 2021/3/17 17:37
     */
    @GetResource(name = "获取当前用户的用户信息", path = "/user-info", requiredPermission = false)
    public ResponseData<CurrentUserInfoResponse> getCurrentLoginUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 转化返回结果
        CurrentUserInfoResponse currentUserInfoResponse = UserLoginInfoFactory.parseUserInfo(loginUser);

        return ResponseData.ok(currentUserInfoResponse);
    }

    /**
     * 获取当前用户的所有Button code
     *
     * @return
     */
    @GetResource(name = "获取当前用户的Button Codes", path = "/perm-code", requiredPermission = false)
    public ResponseData<Set<String>> getCurrentUserPermCodes() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        // 转化返回结果
        return ResponseData.ok(loginUser.getButtonCodes());
    }

    /**
     * 校验token是否正确
     *
     * @date 2021/6/18 15:26
     */
    @PostResource(name = "校验token是否正确", path = "/validate-token", requiredPermission = false, requiredLogin = false)
    public ResponseData<Boolean> validateToken(@RequestBody @Valid ValidateTokenRequest validateTokenRequest) {
        boolean haveSessionFlag = sessionManagerApi.haveSession(validateTokenRequest.getToken());
        return ResponseData.ok(haveSessionFlag);
    }

    /**
     * 取消帐号冻结
     *
     * @date 2022/1/22 16:40
     */
    @PostResource(name = "取消帐号冻结", path = "/cancel-freeze")
    public ResponseData<Void> cancelFreeze(@RequestBody LoginRequest loginRequest) {
        authServiceApi.cancelFreeze(loginRequest);
        return ResponseData.OK_VOID;
    }
}
