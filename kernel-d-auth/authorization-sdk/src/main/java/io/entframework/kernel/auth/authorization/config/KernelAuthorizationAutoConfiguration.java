/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.authorization.config;

import io.entframework.kernel.auth.api.PermissionServiceApi;
import io.entframework.kernel.auth.authorization.impl.PermissionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 鉴权模块的自动配置
 *
 * @author jeff_qian
 * @date 2020/11/30 22:16
 */
@Configuration
public class KernelAuthorizationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PermissionServiceApi.class)
    public PermissionServiceApi permissionServiceApi() {
        return new PermissionServiceImpl();
    }

}
