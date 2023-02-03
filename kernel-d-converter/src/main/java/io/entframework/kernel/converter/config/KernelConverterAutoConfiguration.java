/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.converter.config;

import io.entframework.kernel.converter.support.ConverterService;
import io.entframework.kernel.converter.support.ObjectConversionService;
import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.converter.support.ObjectConverterRegistry;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

@Configuration
public class KernelConverterAutoConfiguration {

    private final ListableBeanFactory beanFactory;

    public KernelConverterAutoConfiguration(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Primary
    @Bean
    public ObjectConversionService objectConversionService() {
        ConverterService converterService = new ConverterService();
        addBeans(converterService, this.beanFactory);
        return converterService;
    }

    @SuppressWarnings("raw")
    public static void addBeans(ObjectConverterRegistry registry, ListableBeanFactory beanFactory) {
        Collection<ObjectConverter> objectConverters = beanFactory.getBeansOfType(ObjectConverter.class).values();
        objectConverters.forEach(registry::addConverter);
    }
}
