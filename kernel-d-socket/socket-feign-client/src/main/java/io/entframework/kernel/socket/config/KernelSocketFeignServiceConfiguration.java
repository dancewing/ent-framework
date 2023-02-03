package io.entframework.kernel.socket.config;

import io.entframework.kernel.cloud.feign.CloudFeignFactory;
import io.entframework.kernel.socket.api.SocketClientOperatorApi;
import io.entframework.kernel.socket.feign.SocketOperatorServiceWrapper;
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
public class KernelSocketFeignServiceConfiguration {

    @Value("${kernel.base-server.socket:http://ent-admin}")
    private String baseServerUrl;

    @Bean
    @ConditionalOnMissingBean(SocketClientOperatorApi.class)
    public SocketClientOperatorApi socketClientOperatorApi(CloudFeignFactory cloudFeignFactory){
        SocketClientOperatorApi feignClient = cloudFeignFactory.build(SocketClientOperatorApi.class, baseServerUrl);
        return new SocketOperatorServiceWrapper(feignClient);
    }
}
