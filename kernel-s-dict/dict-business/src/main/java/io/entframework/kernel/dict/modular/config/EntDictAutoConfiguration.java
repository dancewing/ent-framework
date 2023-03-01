package io.entframework.kernel.dict.modular.config;

import io.entframework.kernel.dict.modular.service.SysDictService;
import io.entframework.kernel.dict.modular.service.SysDictTypeService;
import io.entframework.kernel.dict.modular.service.impl.SysDictServiceImpl;
import io.entframework.kernel.dict.modular.service.impl.SysDictTypeServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.dict.modular.controller", "io.entframework.kernel.dict.modular.converter", "io.entframework.kernel.dict.modular.service"})
@EntityScan("io.entframework.kernel.dict.modular.entity")
public class EntDictAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SysDictService.class)
    public SysDictService sysDictService() {
        return new SysDictServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SysDictTypeService.class)
    public SysDictTypeService sysDictTypeService() {
        return new SysDictTypeServiceImpl();
    }
}