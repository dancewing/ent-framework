package io.entframework.kernel.file.modular.config;

import io.entframework.kernel.file.modular.repository.SysFileInfoRepository;
import io.entframework.kernel.file.modular.repository.SysFileStorageRepository;
import io.entframework.kernel.file.modular.repository.impl.SysFileInfoRepositoryImpl;
import io.entframework.kernel.file.modular.repository.impl.SysFileStorageRepositoryImpl;
import io.entframework.kernel.file.modular.service.SysFileInfoService;
import io.entframework.kernel.file.modular.service.SysFileStorageService;
import io.entframework.kernel.file.modular.service.impl.SysFileInfoServiceImpl;
import io.entframework.kernel.file.modular.service.impl.SysFileStorageServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.file.modular.controller", "io.entframework.kernel.file.modular.converter", "io.entframework.kernel.file.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.file.modular.mapper")
public class EntFileAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysFileInfoRepository.class)
    public SysFileInfoRepository sysFileInfoRepository() {
        return new SysFileInfoRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysFileInfoService.class)
    public SysFileInfoService sysFileInfoService(SysFileInfoRepository sysFileInfoRepository) {
        return new SysFileInfoServiceImpl(sysFileInfoRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysFileStorageRepository.class)
    public SysFileStorageRepository sysFileStorageRepository() {
        return new SysFileStorageRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysFileStorageService.class)
    public SysFileStorageService sysFileStorageService(SysFileStorageRepository sysFileStorageRepository) {
        return new SysFileStorageServiceImpl(sysFileStorageRepository);
    }
}