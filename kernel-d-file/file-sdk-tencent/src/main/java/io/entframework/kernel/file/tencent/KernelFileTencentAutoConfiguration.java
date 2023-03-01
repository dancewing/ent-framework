/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.tencent;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.api.config.TenCosProperties;
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
@EnableConfigurationProperties({ TenCosProperties.class })
public class KernelFileTencentAutoConfiguration {

    /**
     * 本地文件操作
     *
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(name = "tenFileOperator")
    public FileOperatorApi tenFileOperator(TenCosProperties tenCosProperties,
            FileServerProperties fileServerProperties) {
        return new TenFileOperator(tenCosProperties, fileServerProperties);
    }

}
