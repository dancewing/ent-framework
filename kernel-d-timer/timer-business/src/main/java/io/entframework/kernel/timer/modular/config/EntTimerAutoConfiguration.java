package io.entframework.kernel.timer.modular.config;

import io.entframework.kernel.timer.modular.repository.SysTimersRepository;
import io.entframework.kernel.timer.modular.repository.impl.SysTimersRepositoryImpl;
import io.entframework.kernel.timer.modular.service.SysTimersService;
import io.entframework.kernel.timer.modular.service.impl.SysTimersServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.timer.modular.controller", "io.entframework.kernel.timer.modular.converter", "io.entframework.kernel.timer.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.timer.modular.mapper")
public class EntTimerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysTimersRepository.class)
    public SysTimersRepository sysTimersRepository() {
        return new SysTimersRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysTimersService.class)
    public SysTimersService sysTimersService(SysTimersRepository sysTimersRepository) {
        return new SysTimersServiceImpl(sysTimersRepository);
    }
}