package io.entframework.kernel.system.modular.notice.config;

import io.entframework.kernel.system.modular.notice.repository.SysNoticeRepository;
import io.entframework.kernel.system.modular.notice.repository.impl.SysNoticeRepositoryImpl;
import io.entframework.kernel.system.modular.notice.service.SysNoticeService;
import io.entframework.kernel.system.modular.notice.service.impl.SysNoticeServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.system.modular.notice.controller", "io.entframework.kernel.system.modular.notice.converter", "io.entframework.kernel.system.modular.notice.service"})
@MapperScan(basePackages = "io.entframework.kernel.system.modular.notice.mapper")
public class EntNoticeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysNoticeRepository.class)
    public SysNoticeRepository sysNoticeRepository() {
        return new SysNoticeRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysNoticeService.class)
    public SysNoticeService sysNoticeService(SysNoticeRepository sysNoticeRepository) {
        return new SysNoticeServiceImpl(sysNoticeRepository);
    }
}