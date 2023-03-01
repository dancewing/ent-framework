package io.entframework.kernel.message.db.config;

import io.entframework.kernel.message.db.service.SysMessageService;
import io.entframework.kernel.message.db.service.impl.SysMessageServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.message.db.controller",
        "io.entframework.kernel.message.db.converter", "io.entframework.kernel.message.db.service" })
@EntityScan("io.entframework.kernel.message.db.entity")
public class EntMessageDBAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SysMessageService.class)
    public SysMessageService sysMessageService() {
        return new SysMessageServiceImpl();
    }

}