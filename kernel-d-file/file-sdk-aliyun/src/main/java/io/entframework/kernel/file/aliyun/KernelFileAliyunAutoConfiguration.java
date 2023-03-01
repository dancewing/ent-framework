/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.aliyun;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.AliyunOssProperties;
import io.entframework.kernel.file.api.config.FileServerProperties;
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
@EnableConfigurationProperties(AliyunOssProperties.class)
public class KernelFileAliyunAutoConfiguration {

	/**
	 * 本地文件操作
	 *
	 * @date 2020/12/1 14:40
	 */
	@Bean
	@ConditionalOnMissingBean(name = "aliyunFileOperator")
	public FileOperatorApi aliyunFileOperator(AliyunOssProperties aliyunOssProperties,
			FileServerProperties fileServerProperties) {
		return new AliyunFileOperator(aliyunOssProperties, fileServerProperties);
	}

}
