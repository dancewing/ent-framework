/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.auth.authorization.filter.base;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.expander.AuthConfigExpander;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.rule.util.AntPathMatcherUtil;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceUrlParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.annotation.Resource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 基础的Filter，一般用在权限过滤
 *
 * @author fengshuonan
 * @date 2020/12/15 22:50
 */
@Slf4j
public abstract class BaseSecurityInterceptor implements HandlerInterceptor {

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 获取当前请求的路径
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replaceAll("/+", "/");

        // 2. 不需要权限过滤的资源，直接放行
        boolean noneSecurityFlag = AntPathMatcherUtil.getAntMatchFLag(requestURI, request.getContextPath(),
                AuthConfigExpander.getNoneSecurityConfig());
        if (noneSecurityFlag) {
            return true;
        }

        // 3. 获取当前用户的token，可能为null
        String token = null;
        try {
            token = LoginContext.me().getToken();
        }
        catch (Exception e) {
            // 不做处理，因为本接口可能是不需要鉴权
        }

        // 4. 如果token不为空，则先判断是否登录过期了，过期了就直接打回，不过期不做处理
        if (CharSequenceUtil.isNotBlank(token)) {
            try {
                sessionManagerApi.validateToken(token);
            }
            catch (AuthException authException) {
                if (AuthExceptionEnum.AUTH_EXPIRED_ERROR.getErrorCode().equals(authException.getErrorCode())) {
                    sessionManagerApi.destroySessionCookie();
                    throw authException;
                }
            }

            // 5. 刷新用户的session的过期时间
            sessionManagerApi.refreshSession(token);
        }

        // 6. 获取ResourceDefinition，可能为null
        ResourceUrlParam resourceUrlParam = new ResourceUrlParam();
        resourceUrlParam.setUrl(requestURI);
        ResourceDefinition resourceDefinition = resourceServiceApi.getResourceByUrl(resourceUrlParam);

        // 7. 资源找不到，则当前url不被权限控制，直接放行
        if (resourceDefinition == null) {
            return true;
        }

        // 8.执行真正过滤器业务，如果拦截器执行不成功会抛出异常
        this.filterAction(request, response, resourceDefinition, token);

        return true;
    }

    /**
     * 过滤器的具体业务执行逻辑
     *
     * @author fengshuonan
     * @date 2020/12/15 22:52
     */
    public abstract void filterAction(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            ResourceDefinition resourceDefinition, String token);

}
