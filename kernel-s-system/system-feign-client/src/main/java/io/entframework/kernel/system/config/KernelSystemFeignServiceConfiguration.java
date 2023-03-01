/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.config;

import io.entframework.kernel.cloud.feign.CloudFeignFactory;
import io.entframework.kernel.system.api.UserClientServiceApi;
import io.entframework.kernel.system.feign.UserClientServiceWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeignClientsConfiguration.class)
public class KernelSystemFeignServiceConfiguration {

	@Value("${kernel.base-server.user:http://ent-admin}")
	private String baseServerUrl;

	@Bean
	@ConditionalOnMissingBean(UserClientServiceApi.class)
	public UserClientServiceApi userClientServiceApi(CloudFeignFactory cloudFeignFactory) {
		UserClientServiceApi feignClient = cloudFeignFactory.build(UserClientServiceApi.class, baseServerUrl);
		return new UserClientServiceWrapper(feignClient);
	}

}
