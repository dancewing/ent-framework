package io.entframework.kernel.file.config;

import io.entframework.kernel.cloud.feign.CloudFeignFactory;
import io.entframework.kernel.file.api.FileInfoClientApi;
import io.entframework.kernel.file.feign.FileInfoClientServiceFeignWrapper;
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
public class KernelFileFeignServiceConfiguration {

    @Value("${kernel.base-server.file:http://ent-misc}")
    private String baseServerUrl;

    @Bean
    @ConditionalOnMissingBean(FileInfoClientApi.class)
    public FileInfoClientApi fileInfoApi(CloudFeignFactory cloudFeignFactory) {
        FileInfoClientApi feignClient = cloudFeignFactory.build(FileInfoClientApi.class, baseServerUrl);
        return new FileInfoClientServiceFeignWrapper(feignClient);
    }

}
