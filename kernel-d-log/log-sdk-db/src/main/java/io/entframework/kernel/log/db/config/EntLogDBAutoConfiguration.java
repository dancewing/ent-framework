package io.entframework.kernel.log.db.config;

import io.entframework.kernel.log.db.repository.SysLogRepository;
import io.entframework.kernel.log.db.repository.impl.SysLogRepositoryImpl;
import io.entframework.kernel.log.db.service.SysLogService;
import io.entframework.kernel.log.db.service.impl.SysLogServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.log.db.controller", "io.entframework.kernel.log.db.converter", "io.entframework.kernel.log.db.service"})
@MapperScan(basePackages = "io.entframework.kernel.log.db.mapper")
public class EntLogDBAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysLogRepository.class)
    public SysLogRepository sysLogRepository() {
        return new SysLogRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysLogService.class)
    public SysLogService sysLogService(SysLogRepository sysLogRepository) {
        return new SysLogServiceImpl(sysLogRepository);
    }
}