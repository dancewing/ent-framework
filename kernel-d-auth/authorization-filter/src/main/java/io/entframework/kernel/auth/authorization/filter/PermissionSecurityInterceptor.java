/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.auth.authorization.filter;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.PermissionServiceApi;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.authorization.filter.base.BaseSecurityInterceptor;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 权限校验的过滤器，用来校验用户有没有访问接口的权限
 *
 * @author fengshuonan
 * @date 2020/12/15 22:46
 */
@Component
@Slf4j
public class PermissionSecurityInterceptor extends BaseSecurityInterceptor {

    /**
     * 资源权限校验API
     */
    @Resource
    private PermissionServiceApi permissionServiceApi;

    @Override
    public void filterAction(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            ResourceDefinition resourceDefinition, String token) {

        // 1. 获取当前请求的路径
        String requestURI = httpServletRequest.getRequestURI();

        Boolean requiredPermissionFlag = resourceDefinition.getRequiredPermissionFlag();
        // 2. 如果需要鉴权
        if (requiredPermissionFlag != null && requiredPermissionFlag) {

            // token为空，返回用户校验失败
            if (CharSequenceUtil.isEmpty(token)) {
                throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
            }

            // 3. 进行当前接口的权限校验
            permissionServiceApi.checkPermission(token, requestURI);
        }
    }

}
