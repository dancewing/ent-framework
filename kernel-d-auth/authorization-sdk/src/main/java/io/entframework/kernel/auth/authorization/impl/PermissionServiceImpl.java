/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.auth.authorization.impl;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.PermissionServiceApi;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;

import jakarta.annotation.Resource;

import java.util.Set;

/**
 * 权限相关的service
 *
 * @date 2020/10/22 15:49
 */
public class PermissionServiceImpl implements PermissionServiceApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public void checkPermission(String token, String requestUrl) {

        // 1. 校验token是否传参
        if (CharSequenceUtil.isEmpty(token)) {
            throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
        }

        // 2. 获取token对应的用户信息
        LoginUser session = sessionManagerApi.getSession(token);
        if (session == null) {
            throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
        }

        // 3. 验证用户有没有当前url的权限
        Set<String> resourceUrls = session.getResourceUrls();
        if (resourceUrls == null || resourceUrls.isEmpty()) {
            throw new AuthException(AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR);
        } else {
            if (!resourceUrls.contains(requestUrl)) {
                throw new AuthException(AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR);
            }
        }
    }

}
