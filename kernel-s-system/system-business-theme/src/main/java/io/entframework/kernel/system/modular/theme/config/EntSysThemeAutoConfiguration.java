package io.entframework.kernel.system.modular.theme.config;

import io.entframework.kernel.system.modular.theme.repository.SysThemeRepository;
import io.entframework.kernel.system.modular.theme.repository.SysThemeTemplateFieldRepository;
import io.entframework.kernel.system.modular.theme.repository.SysThemeTemplateRelRepository;
import io.entframework.kernel.system.modular.theme.repository.SysThemeTemplateRepository;
import io.entframework.kernel.system.modular.theme.repository.impl.SysThemeRepositoryImpl;
import io.entframework.kernel.system.modular.theme.repository.impl.SysThemeTemplateFieldRepositoryImpl;
import io.entframework.kernel.system.modular.theme.repository.impl.SysThemeTemplateRelRepositoryImpl;
import io.entframework.kernel.system.modular.theme.repository.impl.SysThemeTemplateRepositoryImpl;
import io.entframework.kernel.system.modular.theme.service.SysThemeService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateService;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeServiceImpl;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeTemplateFieldServiceImpl;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeTemplateRelServiceImpl;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeTemplateServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.theme.controller", "io.entframework.kernel.system.modular.theme.converter", "io.entframework.kernel.system.modular.theme.service"})
@MapperScan(basePackages = "io.entframework.kernel.system.modular.theme.mapper")
public class EntSysThemeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysThemeRepository.class)
    public SysThemeRepository sysThemeRepository() {
        return new SysThemeRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeService.class)
    public SysThemeService sysThemeService(SysThemeRepository sysThemeRepository) {
        return new SysThemeServiceImpl(sysThemeRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeTemplateRepository.class)
    public SysThemeTemplateRepository sysThemeTemplateRepository() {
        return new SysThemeTemplateRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeTemplateService.class)
    public SysThemeTemplateService sysThemeTemplateService(SysThemeTemplateRepository sysThemeTemplateRepository) {
        return new SysThemeTemplateServiceImpl(sysThemeTemplateRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeTemplateFieldRepository.class)
    public SysThemeTemplateFieldRepository sysThemeTemplateFieldRepository() {
        return new SysThemeTemplateFieldRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeTemplateFieldService.class)
    public SysThemeTemplateFieldService sysThemeTemplateFieldService(SysThemeTemplateFieldRepository sysThemeTemplateFieldRepository) {
        return new SysThemeTemplateFieldServiceImpl(sysThemeTemplateFieldRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeTemplateRelRepository.class)
    public SysThemeTemplateRelRepository sysThemeTemplateRelRepository() {
        return new SysThemeTemplateRelRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysThemeTemplateRelService.class)
    public SysThemeTemplateRelService sysThemeTemplateRelService(SysThemeTemplateRelRepository sysThemeTemplateRelRepository) {
        return new SysThemeTemplateRelServiceImpl(sysThemeTemplateRelRepository);
    }
}