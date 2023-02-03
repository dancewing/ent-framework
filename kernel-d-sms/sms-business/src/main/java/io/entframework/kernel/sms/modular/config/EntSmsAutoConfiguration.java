package io.entframework.kernel.sms.modular.config;

import io.entframework.kernel.sms.modular.repository.SysSmsRepository;
import io.entframework.kernel.sms.modular.repository.impl.SysSmsRepositoryImpl;
import io.entframework.kernel.sms.modular.service.SysSmsService;
import io.entframework.kernel.sms.modular.service.impl.SysSmsServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.sms.modular.controller", "io.entframework.kernel.sms.modular.converter", "io.entframework.kernel.sms.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.sms.modular.mapper")
public class EntSmsAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysSmsRepository.class)
    public SysSmsRepository sysSmsRepository() {
        return new SysSmsRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysSmsService.class)
    public SysSmsService sysSmsService(SysSmsRepository sysSmsRepository) {
        return new SysSmsServiceImpl(sysSmsRepository);
    }
}