/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.monitor.integration.config;

import io.entframework.kernel.monitor.api.PrometheusApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

/**
 * 是否显示prometheus菜单
 *
 * @date 2021/3/3 17:14
 */
@Configuration
@Slf4j
public class PrometheusConfiguration {

    @Value("${prometheus.enabled}")
    private boolean prometheusEnabled;

    @Resource
    private PrometheusApi prometheusApi;

    /***
     * 配置是否开启prometheus相关菜单
     *
     *
     * @date 2021/3/3 17:14
     */
    @Bean
    public void configPrometheusMenu() {
        if (prometheusEnabled) {
            prometheusApi.displayPrometheusMenu();
        } else {
            prometheusApi.closePrometheusMenu();
        }
    }

}
