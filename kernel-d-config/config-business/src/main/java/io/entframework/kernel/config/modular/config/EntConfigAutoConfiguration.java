package io.entframework.kernel.config.modular.config;

import io.entframework.kernel.config.modular.service.SysConfigService;
import io.entframework.kernel.config.modular.service.impl.SysConfigServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.config.modular.controller",
		"io.entframework.kernel.config.modular.converter", "io.entframework.kernel.config.modular.service" })
@EntityScan("io.entframework.kernel.config.modular.entity")
public class EntConfigAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SysConfigService.class)
	public SysConfigService sysConfigService() {
		return new SysConfigServiceImpl();
	}

}