package io.entframework.kernel.oss.config;

import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.oss.interceptor.VirtualHostInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties({ FileServerProperties.class })
@ComponentScan(basePackages = "io.entframework.kernel.oss.controller")
public class EntOssAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor virtualHostInterceptor() {
        return new VirtualHostInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptor) {
        interceptor.addInterceptor(virtualHostInterceptor()).addPathPatterns("/**");
    }

}