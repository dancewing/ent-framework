/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.groovy.starter;

import io.entframework.kernel.groovy.GroovyOperator;
import io.entframework.kernel.groovy.api.GroovyApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Groovy的自动配置
 *
 * @date 2021/1/29 11:23
 */
@Configuration
public class KernelGroovyAutoConfiguration {

    /**
     * Groovy的操作类
     *
     * @date 2021/1/29 11:23
     */
    @Bean
    @ConditionalOnMissingBean(GroovyApi.class)
    public GroovyApi groovyApi() {
        return new GroovyOperator();
    }

}
