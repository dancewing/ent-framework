/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.config.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置模块的自动配置类
 *
 * @author jeff_qian
 * @date 2020/11/30 22:24
 */
@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.config" })
public class KernelSysConfigAutoConfiguration {

    @Bean
    public KernelSysConfigModuleRegister kernelSysConfigModuleRegister() {
        return new KernelSysConfigModuleRegister();
    }

}
