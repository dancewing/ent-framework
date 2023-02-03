package io.entframework.kernel.message.db.config;

import io.entframework.kernel.message.db.repository.SysMessageRepository;
import io.entframework.kernel.message.db.repository.impl.SysMessageRepositoryImpl;
import io.entframework.kernel.message.db.service.SysMessageService;
import io.entframework.kernel.message.db.service.impl.SysMessageServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.message.db.controller", "io.entframework.kernel.message.db.converter", "io.entframework.kernel.message.db.service"})
@MapperScan(basePackages = "io.entframework.kernel.message.db.mapper")
public class EntMessageDBAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysMessageRepository.class)
    public SysMessageRepository sysMessageRepository() {
        return new SysMessageRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysMessageService.class)
    public SysMessageService sysMessageService(SysMessageRepository sysMessageRepository) {
        return new SysMessageServiceImpl(sysMessageRepository);
    }
}