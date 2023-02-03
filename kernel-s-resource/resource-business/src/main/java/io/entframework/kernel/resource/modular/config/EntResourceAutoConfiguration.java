package io.entframework.kernel.resource.modular.config;

import io.entframework.kernel.resource.modular.repository.SysResourceRepository;
import io.entframework.kernel.resource.modular.repository.impl.SysResourceRepositoryImpl;
import io.entframework.kernel.resource.modular.service.SysResourceService;
import io.entframework.kernel.resource.modular.service.impl.SysResourceServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.resource.modular.controller", "io.entframework.kernel.resource.modular.converter", "io.entframework.kernel.resource.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.resource.modular.mapper")
public class EntResourceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysResourceRepository.class)
    public SysResourceRepository sysResourceRepository() {
        return new SysResourceRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysResourceService.class)
    public SysResourceService sysResourceService(SysResourceRepository sysResourceRepository) {
        return new SysResourceServiceImpl(sysResourceRepository);
    }
}