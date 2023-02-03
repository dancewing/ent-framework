/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.i18n.starter;

import io.entframework.kernel.i18n.TranslationContainer;
import io.entframework.kernel.i18n.api.TranslationApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 多语言翻译的自动配置
 *
 * @date 2021/1/24 16:42
 */
@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.i18n"})
public class KernelTranslationAutoConfiguration {

    /**
     * 多语言翻译条目存放容器
     *
     * @date 2021/1/24 19:42
     */
    @Bean
    public TranslationApi translationApi() {
        return new TranslationContainer();
    }

    @Bean
    public KernelTranslationModuleRegister kernelTranslationModuleRegister(){
        return new KernelTranslationModuleRegister();
    }

}
