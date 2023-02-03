/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.validator.starter;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Guns的校验器的自动配置
 *
 * @date 2021/3/18 16:03
 */
@Configuration
@AutoConfigureBefore(ValidationAutoConfiguration.class)
public class KernelValidatorAutoConfiguration {

    /**
     * 自定义的spring参数校验器，重写主要为了保存一些在自定义validator中读不到的属性
     *
     * @date 2020/8/12 20:18
     */
    @Bean
    public KernelValidator gunsValidator() {
        return new KernelValidator();
    }

}
