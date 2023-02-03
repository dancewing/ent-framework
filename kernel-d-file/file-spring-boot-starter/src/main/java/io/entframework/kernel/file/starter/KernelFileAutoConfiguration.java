/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.file.starter;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.modular.factory.FileOperatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * 文件的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 14:34
 */
@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.file"})
@EnableConfigurationProperties(FileServerProperties.class)
@Slf4j
public class KernelFileAutoConfiguration {

    @Bean
    public FileOperatorFactory fileOperatorFactory(ObjectProvider<Collection<FileOperatorApi>> fileOperatorApis) {
        return new FileOperatorFactory(fileOperatorApis.getIfAvailable());
    }

    @Bean
    public KernelFileModuleRegister kernelFileModuleRegister() {
        return new KernelFileModuleRegister();
    }
}
