/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.auth.authorization.filter;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.authorization.filter.base.BaseSecurityInterceptor;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 鉴权的过滤器，用来鉴权token
 *
 * @author fengshuonan
 * @date 2020/12/15 22:45
 */
@Component
@Slf4j
public class AuthJwtTokenSecurityInterceptor extends BaseSecurityInterceptor {

    /**
     * 登陆服务Api
     */
    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public void filterAction(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            ResourceDefinition resourceDefinition, String token) {

        // 1. 获取当前请求的路径
        String requestURI = httpServletRequest.getRequestURI();
        Boolean requiredLoginFlag = resourceDefinition.getRequiredLoginFlag();
        // 2. 如果需要登录
        if (requiredLoginFlag != null && requiredLoginFlag) {

            // token为空，返回用户校验失败
            if (CharSequenceUtil.isEmpty(token)) {
                throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
            }

            // 3.校验token和用户会话信息是否正确
            sessionManagerApi.checkAuth(token, requestURI);
        }
    }

}
