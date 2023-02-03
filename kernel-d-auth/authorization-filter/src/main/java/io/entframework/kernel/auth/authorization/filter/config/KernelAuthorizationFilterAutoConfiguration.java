/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.authorization.filter.config;

import io.entframework.kernel.auth.authorization.filter.AuthJwtTokenSecurityInterceptor;
import io.entframework.kernel.auth.authorization.filter.PermissionSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Filter的自动配置
 *
 * @author jeff_qian
 * @date 2020/11/30 22:16
 */
@Configuration
public class KernelAuthorizationFilterAutoConfiguration implements WebMvcConfigurer {


    @Bean
    public PermissionSecurityInterceptor permissionSecurityInterceptor() {
        return new PermissionSecurityInterceptor();
    }

    @Bean
    public AuthJwtTokenSecurityInterceptor authJwtTokenSecurityInterceptor() {
        return new AuthJwtTokenSecurityInterceptor();
    }

}
