package io.entframework.kernel.system.modular.home.config;

import io.entframework.kernel.system.modular.home.repository.SysStatisticsCountRepository;
import io.entframework.kernel.system.modular.home.repository.SysStatisticsUrlRepository;
import io.entframework.kernel.system.modular.home.repository.impl.SysStatisticsCountRepositoryImpl;
import io.entframework.kernel.system.modular.home.repository.impl.SysStatisticsUrlRepositoryImpl;
import io.entframework.kernel.system.modular.home.service.SysStatisticsCountService;
import io.entframework.kernel.system.modular.home.service.SysStatisticsUrlService;
import io.entframework.kernel.system.modular.home.service.impl.SysStatisticsCountServiceImpl;
import io.entframework.kernel.system.modular.home.service.impl.SysStatisticsUrlServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.home.controller", "io.entframework.kernel.system.modular.home.converter", "io.entframework.kernel.system.modular.home.service"})
@MapperScan(basePackages = "io.entframework.kernel.system.modular.home.mapper")
public class EntSysHomeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysStatisticsCountRepository.class)
    public SysStatisticsCountRepository sysStatisticsCountRepository() {
        return new SysStatisticsCountRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysStatisticsCountService.class)
    public SysStatisticsCountService sysStatisticsCountService(SysStatisticsCountRepository sysStatisticsCountRepository) {
        return new SysStatisticsCountServiceImpl(sysStatisticsCountRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysStatisticsUrlRepository.class)
    public SysStatisticsUrlRepository sysStatisticsUrlRepository() {
        return new SysStatisticsUrlRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysStatisticsUrlService.class)
    public SysStatisticsUrlService sysStatisticsUrlService(SysStatisticsUrlRepository sysStatisticsUrlRepository) {
        return new SysStatisticsUrlServiceImpl(sysStatisticsUrlRepository);
    }
}