package io.entframework.kernel.timer.modular.config;

import io.entframework.kernel.timer.modular.service.SysTimersService;
import io.entframework.kernel.timer.modular.service.impl.SysTimersServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.timer.modular.controller",
        "io.entframework.kernel.timer.modular.converter", "io.entframework.kernel.timer.modular.service" })
@EntityScan("io.entframework.kernel.timer.modular.entity")
public class EntTimerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SysTimersService.class)
    public SysTimersService sysTimersService() {
        return new SysTimersServiceImpl();
    }

}