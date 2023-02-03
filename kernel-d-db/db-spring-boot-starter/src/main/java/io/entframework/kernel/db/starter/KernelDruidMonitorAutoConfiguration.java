/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.starter;

import io.entframework.kernel.db.api.config.DruidAdminProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * druid监控的自动配置类
 *
 * @author jeff_qian
 * @date 2021/1/24 11:27
 */
@Configuration
@EnableConfigurationProperties({DruidAdminProperties.class})
public class KernelDruidMonitorAutoConfiguration {

    /**
     * Druid监控界面的配置
     *
     * @date 2021/1/10 11:29
     */
//    @Bean
//    @ConditionalOnProperty(prefix = "kernel.druid.admin", name = "enabled", havingValue = "true", matchIfMissing = true)
//    public ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean(DruidAdminProperties druidAdminProperties) {
//        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
//        registrationBean.setServlet(new StatViewServlet());
//        registrationBean.addUrlMappings(druidAdminProperties.getUrlMappings());
//        registrationBean.addInitParameter("loginUsername", druidAdminProperties.getAccount());
//        registrationBean.addInitParameter("loginPassword", druidAdminProperties.getPassword());
//        registrationBean.addInitParameter("resetEnable", String.valueOf(druidAdminProperties.isEnableReset()));
//        return registrationBean;
//    }

    /**
     * 用于配置Druid监控url统计
     *
     * @date 2021/1/10 11:45
     */
//    @Bean
//    @ConditionalOnProperty(prefix = "kernel.druid.admin", name = "enabled", havingValue = "true", matchIfMissing = true)
//    public FilterRegistrationBean<WebStatFilter> webStatFilterRegistrationBean(DruidAdminProperties druidAdminProperties) {
//        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>();
//        WebStatFilter filter = new WebStatFilter();
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns(druidAdminProperties.getWebStatFilterUrlPattern());
//        registrationBean.addInitParameter("exclusions", druidAdminProperties.getWebStatFilterExclusions());
//        registrationBean.addInitParameter("sessionStatEnable", String.valueOf(druidAdminProperties.isEnableWebStatFilterSessionStat()));
//        registrationBean.addInitParameter("sessionStatMaxCount", String.valueOf(druidAdminProperties.getWebStatFilterSessionStatMaxCount()));
//        if (CharSequenceUtil.isNotBlank(druidAdminProperties.getWebStatFilterSessionName())) {
//            registrationBean.addInitParameter("principalSessionName", druidAdminProperties.getWebStatFilterSessionName());
//        }
//        if (CharSequenceUtil.isNotBlank(druidAdminProperties.getWebStatFilterPrincipalCookieName())) {
//            registrationBean.addInitParameter("principalCookieName", druidAdminProperties.getWebStatFilterPrincipalCookieName());
//        }
//        registrationBean.addInitParameter("profileEnable", String.valueOf(druidAdminProperties.isEnableWebStatFilterProfile()));
//        return registrationBean;
//    }

    /**
     * 解决druid discard long time none received connection问题
     *
     * @date 2021/7/7 14:15
     */
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }

}
