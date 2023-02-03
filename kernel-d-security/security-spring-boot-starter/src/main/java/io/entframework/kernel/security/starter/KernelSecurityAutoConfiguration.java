/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.starter;

import io.entframework.kernel.security.api.config.KernelSecurityProperties;
import io.entframework.kernel.security.api.constants.SecurityConstants;
import io.entframework.kernel.security.clear.ClearThreadLocalFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 安全模块自动配置
 *
 * @date 2021/2/19 9:05
 */
@Configuration
@EnableConfigurationProperties({KernelSecurityProperties.class})
public class KernelSecurityAutoConfiguration {

    /**
     * ThreadLocal清除器
     *
     * @date 2021/10/29 11:29
     */
    @Bean
    public FilterRegistrationBean<ClearThreadLocalFilter> clearThreadLocalFilterFilterRegistrationBean() {
        FilterRegistrationBean<ClearThreadLocalFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ClearThreadLocalFilter());
        bean.addUrlPatterns(SecurityConstants.DEFAULT_XSS_PATTERN);
        bean.setName(ClearThreadLocalFilter.NAME);
        bean.setOrder(HIGHEST_PRECEDENCE + 1);
        return bean;
    }

}
