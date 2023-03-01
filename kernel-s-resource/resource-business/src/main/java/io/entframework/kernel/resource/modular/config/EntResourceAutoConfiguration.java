package io.entframework.kernel.resource.modular.config;

import io.entframework.kernel.resource.modular.service.SysResourceService;
import io.entframework.kernel.resource.modular.service.impl.SysResourceServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.resource.modular.controller",
		"io.entframework.kernel.resource.modular.converter", "io.entframework.kernel.resource.modular.service" })
@EntityScan("io.entframework.kernel.resource.modular.entity")
public class EntResourceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SysResourceService.class)
	public SysResourceService sysResourceService() {
		return new SysResourceServiceImpl();
	}

}