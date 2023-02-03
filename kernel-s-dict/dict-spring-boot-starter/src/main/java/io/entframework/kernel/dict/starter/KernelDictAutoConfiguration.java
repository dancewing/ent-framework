/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.dict.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 字典业务的自动配置
 *
 * @date 2020/12/1 21:54
 */
@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.dict"})
public class KernelDictAutoConfiguration {

    @Bean
    public KernelDictModuleRegister kernelDictModuleRegister() {
        return new KernelDictModuleRegister();
    }

}
