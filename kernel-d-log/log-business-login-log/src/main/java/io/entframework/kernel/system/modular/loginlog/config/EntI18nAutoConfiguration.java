package io.entframework.kernel.system.modular.loginlog.config;

import io.entframework.kernel.system.modular.loginlog.repository.SysLoginLogRepository;
import io.entframework.kernel.system.modular.loginlog.repository.impl.SysLoginLogRepositoryImpl;
import io.entframework.kernel.system.modular.loginlog.service.SysLoginLogService;
import io.entframework.kernel.system.modular.loginlog.service.impl.SysLoginLogServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.loginlog.controller", "io.entframework.kernel.system.modular.loginlog.converter", "io.entframework.kernel.system.modular.loginlog.service"})
@MapperScan(basePackages = "io.entframework.kernel.system.modular.loginlog.mapper")
public class EntI18nAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysLoginLogRepository.class)
    public SysLoginLogRepository sysLoginLogRepository() {
        return new SysLoginLogRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysLoginLogService.class)
    public SysLoginLogService sysLoginLogService(SysLoginLogRepository sysLoginLogRepository) {
        return new SysLoginLogServiceImpl(sysLoginLogRepository);
    }
}