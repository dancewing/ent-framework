package io.entframework.kernel.system.modular.home.config;

import io.entframework.kernel.system.modular.home.service.SysStatisticsCountService;
import io.entframework.kernel.system.modular.home.service.SysStatisticsUrlService;
import io.entframework.kernel.system.modular.home.service.impl.SysStatisticsCountServiceImpl;
import io.entframework.kernel.system.modular.home.service.impl.SysStatisticsUrlServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.home.controller", "io.entframework.kernel.system.modular.home.converter", "io.entframework.kernel.system.modular.home.service"})
public class EntSysHomeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysStatisticsCountService.class)
    public SysStatisticsCountService sysStatisticsCountService() {
        return new SysStatisticsCountServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysStatisticsUrlService.class)
    public SysStatisticsUrlService sysStatisticsUrlService() {
        return new SysStatisticsUrlServiceImpl();
    }
}