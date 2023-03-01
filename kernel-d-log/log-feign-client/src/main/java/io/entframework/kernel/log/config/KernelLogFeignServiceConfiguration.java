package io.entframework.kernel.log.config;

import io.entframework.kernel.cloud.feign.CloudFeignFactory;
import io.entframework.kernel.log.api.LogRecordApi;
import io.entframework.kernel.log.api.LoginLogServiceApi;
import io.entframework.kernel.log.feign.LogRecordServiceFeignWrapper;
import io.entframework.kernel.log.feign.LoginLogServiceFeignWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeignClientsConfiguration.class)
public class KernelLogFeignServiceConfiguration {

    @Value("${kernel.base-server.log:http://ent-misc}")
    private String baseServerUrl;

    @Bean
    @ConditionalOnMissingBean(LoginLogServiceApi.class)
    public LoginLogServiceApi loginLogServiceApi(CloudFeignFactory cloudFeignFactory) {
        LoginLogServiceApi feignClient = cloudFeignFactory.asyncBuild(LoginLogServiceApi.class, baseServerUrl);
        return new LoginLogServiceFeignWrapper(feignClient);
    }

    @Bean
    @ConditionalOnMissingBean(LogRecordApi.class)
    public LogRecordApi logRecordApi(CloudFeignFactory cloudFeignFactory) {
        LogRecordApi feignClient = cloudFeignFactory.asyncBuild(LogRecordApi.class, baseServerUrl);
        return new LogRecordServiceFeignWrapper(feignClient);
    }

}
