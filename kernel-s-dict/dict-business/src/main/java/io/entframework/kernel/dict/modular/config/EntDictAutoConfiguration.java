package io.entframework.kernel.dict.modular.config;

import io.entframework.kernel.dict.modular.repository.SysDictRepository;
import io.entframework.kernel.dict.modular.repository.SysDictTypeRepository;
import io.entframework.kernel.dict.modular.repository.impl.SysDictRepositoryImpl;
import io.entframework.kernel.dict.modular.repository.impl.SysDictTypeRepositoryImpl;
import io.entframework.kernel.dict.modular.service.SysDictService;
import io.entframework.kernel.dict.modular.service.SysDictTypeService;
import io.entframework.kernel.dict.modular.service.impl.SysDictServiceImpl;
import io.entframework.kernel.dict.modular.service.impl.SysDictTypeServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.dict.modular.controller", "io.entframework.kernel.dict.modular.converter", "io.entframework.kernel.dict.modular.service"})
@MapperScan(basePackages = "io.entframework.kernel.dict.modular.mapper")
public class EntDictAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysDictRepository.class)
    public SysDictRepository sysDictRepository() {
        return new SysDictRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysDictService.class)
    public SysDictService sysDictService(SysDictRepository sysDictRepository) {
        return new SysDictServiceImpl(sysDictRepository);
    }

    @Bean
    @ConditionalOnMissingBean(SysDictTypeRepository.class)
    public SysDictTypeRepository sysDictTypeRepository() {
        return new SysDictTypeRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysDictTypeService.class)
    public SysDictTypeService sysDictTypeService(SysDictTypeRepository sysDictTypeRepository) {
        return new SysDictTypeServiceImpl(sysDictTypeRepository);
    }
}