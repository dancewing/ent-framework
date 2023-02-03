package io.entframework.kernel.config.modular.config;

import io.entframework.kernel.config.modular.repository.SysConfigRepository;
import io.entframework.kernel.config.modular.repository.impl.SysConfigRepositoryImpl;
import io.entframework.kernel.config.modular.service.SysConfigService;
import io.entframework.kernel.config.modular.service.impl.SysConfigServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.config.modular.controller", "io.entframework.kernel.config.modular.converter", "io.entframework.kernel.config.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.config.modular.mapper")
public class EntConfigAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysConfigRepository.class)
    public SysConfigRepository sysConfigRepository() {
        return new SysConfigRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysConfigService.class)
    public SysConfigService sysConfigService(SysConfigRepository sysConfigRepository) {
        return new SysConfigServiceImpl(sysConfigRepository);
    }
}