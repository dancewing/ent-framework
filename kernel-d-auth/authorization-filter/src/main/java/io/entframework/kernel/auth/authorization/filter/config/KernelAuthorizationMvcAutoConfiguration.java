/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.auth.authorization.filter.config;

import io.entframework.kernel.auth.authorization.filter.AuthJwtTokenSecurityInterceptor;
import io.entframework.kernel.auth.authorization.filter.PermissionSecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;

@Configuration
public class KernelAuthorizationMvcAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private AuthJwtTokenSecurityInterceptor authJwtTokenSecurityInterceptor;

    @Resource
    private PermissionSecurityInterceptor permissionSecurityInterceptor;

    /**
     * 配置项目拦截器
     *
     * @author fengshuonan
     * @date 2020/12/18 9:43
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authJwtTokenSecurityInterceptor);
        registry.addInterceptor(permissionSecurityInterceptor);
    }

}
