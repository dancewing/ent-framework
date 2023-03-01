package io.entframework.kernel.system.modular.loginlog.config;

import io.entframework.kernel.system.modular.loginlog.service.SysLoginLogService;
import io.entframework.kernel.system.modular.loginlog.service.impl.SysLoginLogServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.loginlog.controller", "io.entframework.kernel.system.modular.loginlog.converter", "io.entframework.kernel.system.modular.loginlog.service"})
@EntityScan("io.entframework.kernel.system.modular.loginlog.entity")
public class EntI18nAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysLoginLogService.class)
    public SysLoginLogService sysLoginLogService() {
        return new SysLoginLogServiceImpl();
    }
}