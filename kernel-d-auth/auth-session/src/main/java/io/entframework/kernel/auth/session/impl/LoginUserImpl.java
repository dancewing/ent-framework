/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.auth.session.impl;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.config.AuthConfigProperties;
import io.entframework.kernel.auth.api.context.LoginUserHolder;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.rule.util.HttpServletUtil;

import jakarta.annotation.Resource;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 当前登陆用户的接口实现
 *
 * @author jeff_qian
 * @date 2020/10/21 18:09
 */
public class LoginUserImpl implements LoginUserApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private AuthConfigProperties authConfigProperties;

    @Override
    public String getToken() {
        // 获取当前http请求
        HttpServletRequest request = HttpServletUtil.getRequest();

        // 1. 优先从param参数中获取token
        String parameterToken = request.getParameter(authConfigProperties.getTokenParamName());

        // 不为空则直接返回param的token
        if (CharSequenceUtil.isNotBlank(parameterToken)) {
            return parameterToken;
        }

        // 2. 从header中获取token
        String authToken = request.getHeader(authConfigProperties.getTokenHeaderName());
        if (CharSequenceUtil.isNotBlank(authToken)) {
            return authToken;
        }

        // 3. 从cookie中获取token
        String sessionCookieName = authConfigProperties.getSessionCookieName();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {

                // 如果cookie有对应的值，并且不为空
                if (sessionCookieName.equals(cookie.getName()) && CharSequenceUtil.isNotBlank(cookie.getValue())) {
                    return cookie.getValue();
                }
            }
        }

        // 获取不到token，直接告诉用户
        throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
    }

    @Override
    public LoginUser getLoginUser() throws AuthException {

        // 先从ThreadLocal中获取
        LoginUser currentUser = LoginUserHolder.get();
        if (currentUser != null) {
            return currentUser;
        }

        // 获取用户的token
        String token = getToken();

        // 获取session中该token对应的用户
        LoginUser session = sessionManagerApi.getSession(token);

        // session为空抛出异常
        if (session == null) {
            throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
        }

        return session;
    }

    @Override
    public LoginUser getLoginUserNullable() {

        // 先从ThreadLocal中获取
        LoginUser currentUser = LoginUserHolder.get();
        if (currentUser != null) {
            return currentUser;
        }

        // 获取用户的token
        String token = null;
        try {
            token = getToken();
        }
        catch (Exception e) {
            return null;
        }

        // 获取session中该token对应的用户
        return sessionManagerApi.getSession(token);

    }

    @Override
    public boolean getSuperAdminFlag() {
        LoginUser loginUser = getLoginUser();
        return loginUser.getSuperAdmin();
    }

    @Override
    public boolean hasLogin() {

        // 获取用户的token
        String token = null;
        try {
            token = getToken();
        }
        catch (Exception e) {
            return false;
        }

        // 获取是否在会话中有
        return sessionManagerApi.haveSession(token);
    }

    @Override
    public boolean haveButton(String buttonCode) {
        LoginUser loginUser = getLoginUser();
        if (loginUser.getButtonCodes() == null) {
            return false;
        }
        else {
            return loginUser.getButtonCodes().contains(buttonCode);
        }
    }

}
