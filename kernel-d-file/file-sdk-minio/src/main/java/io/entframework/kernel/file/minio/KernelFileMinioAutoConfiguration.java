/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.minio;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.api.config.MinIoProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 14:34
 */
@Configuration
@EnableConfigurationProperties({ MinIoProperties.class })
public class KernelFileMinioAutoConfiguration {

    /**
     * 本地文件操作
     *
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(name = "minIoFileOperator")
    public FileOperatorApi minIoFileOperator(MinIoProperties minIoProperties,
            FileServerProperties fileServerProperties) {
        return new MinIoFileOperator(minIoProperties, fileServerProperties);
    }

}
