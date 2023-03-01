/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.starter;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.scanner.ApiResourceScanner;
import io.entframework.kernel.scanner.DefaultResourceCollector;
import io.entframework.kernel.scanner.api.ResourceCollectorApi;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import io.entframework.kernel.scanner.api.pojo.devops.DevOpsReportProperties;
import io.entframework.kernel.scanner.api.pojo.scanner.ScannerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资源的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 17:24
 */
@Configuration
@EnableConfigurationProperties({ ScannerProperties.class, DevOpsReportProperties.class })
public class KernelResourceAutoConfiguration {

	@Value("${spring.application.name:}")
	private String springApplicationName;

	/**
	 * 资源扫描器
	 *
	 * @date 2020/12/1 17:29
	 */
	@Bean
	@ConditionalOnMissingBean(ApiResourceScanner.class)
	@ConditionalOnProperty(prefix = ScannerConstants.SCANNER_PREFIX, name = "open", havingValue = "true")
	public ApiResourceScanner apiResourceScanner(ResourceCollectorApi resourceCollectorApi,
			ScannerProperties scannerProperties) {
		if (CharSequenceUtil.isBlank(scannerProperties.getAppCode())
				&& CharSequenceUtil.isBlank(springApplicationName)) {
			throw new RuntimeException("请设置spring.application.name或者kernel.scanner.app-code");
		}
		if (CharSequenceUtil.isBlank(scannerProperties.getAppCode())) {
			scannerProperties.setAppCode(springApplicationName);
		}
		return new ApiResourceScanner(resourceCollectorApi, scannerProperties);
	}

	/**
	 * 资源搜集器
	 *
	 * @date 2020/12/1 17:29
	 */
	@Bean
	@ConditionalOnMissingBean(ResourceCollectorApi.class)
	@ConditionalOnProperty(prefix = ScannerConstants.SCANNER_PREFIX, name = "open", havingValue = "true")
	public ResourceCollectorApi resourceCollectorApi() {
		return new DefaultResourceCollector();
	}

}
