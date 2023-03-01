package io.entframework.kernel.log.db.config;

import io.entframework.kernel.log.db.service.SysLogService;
import io.entframework.kernel.log.db.service.impl.SysLogServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.log.db.controller", "io.entframework.kernel.log.db.converter", "io.entframework.kernel.log.db.service"})
@EntityScan("io.entframework.kernel.log.db.entity")
public class EntLogDBAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysLogService.class)
    public SysLogService sysLogService() {
        return new SysLogServiceImpl();
    }
}