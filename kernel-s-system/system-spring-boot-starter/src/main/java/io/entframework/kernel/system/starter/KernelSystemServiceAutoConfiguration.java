/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统服务类自动注册
 */
@Configuration
@ComponentScan(basePackages = "io.entframework.kernel.system.starter")
public class KernelSystemServiceAutoConfiguration {

    @Bean
    public KernelSystemModuleRegister kernelSystemModuleRegister(){
        return new KernelSystemModuleRegister();
    }
}
