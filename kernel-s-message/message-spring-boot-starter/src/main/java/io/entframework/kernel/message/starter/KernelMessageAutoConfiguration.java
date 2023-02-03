/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统消息的自动配置
 *
 * @date 2020/12/31 18:50
 */
@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.message"})
public class KernelMessageAutoConfiguration {

    @Bean
    public KernelMessageModuleRegister kernelMessageModuleRegister() {
        return new KernelMessageModuleRegister();
    }

}
