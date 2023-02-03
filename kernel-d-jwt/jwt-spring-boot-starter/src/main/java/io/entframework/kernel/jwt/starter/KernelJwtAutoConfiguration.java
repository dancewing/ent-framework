/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.jwt.starter;

import io.entframework.kernel.jwt.JwtTokenOperator;
import io.entframework.kernel.jwt.api.JwtApi;
import io.entframework.kernel.jwt.api.config.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jwt的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 14:34
 */
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
public class KernelJwtAutoConfiguration {

    /**
     * jwt操作工具类的配置
     *
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(JwtApi.class)
    public JwtApi jwtApi(JwtProperties jwtProperties) {
        return new JwtTokenOperator(jwtProperties);
    }

}
