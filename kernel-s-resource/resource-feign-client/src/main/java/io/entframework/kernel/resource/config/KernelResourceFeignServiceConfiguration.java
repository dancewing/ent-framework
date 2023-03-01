package io.entframework.kernel.resource.config;

import io.entframework.kernel.cloud.feign.CloudFeignFactory;
import io.entframework.kernel.resource.feign.ResourceReportServiceWrapper;
import io.entframework.kernel.scanner.api.ResourceReportApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jeff_qian
 */
@Configuration
@Import(FeignClientsConfiguration.class)
public class KernelResourceFeignServiceConfiguration {

	@Value("${kernel.base-server.resource:http://ent-admin}")
	private String baseServerUrl;

	@Bean
	@ConditionalOnMissingBean(ResourceReportApi.class)
	public ResourceReportApi resourceReportApi(CloudFeignFactory cloudFeignFactory) {
		ResourceReportApi feignClient = cloudFeignFactory.build(ResourceReportApi.class, baseServerUrl);
		return new ResourceReportServiceWrapper(feignClient);
	}

}
