package io.entframework.kernel.system.modular.theme.config;

import io.entframework.kernel.system.modular.theme.service.SysThemeService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateService;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeServiceImpl;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeTemplateFieldServiceImpl;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeTemplateRelServiceImpl;
import io.entframework.kernel.system.modular.theme.service.impl.SysThemeTemplateServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.system.modular.theme.controller",
		"io.entframework.kernel.system.modular.theme.converter",
		"io.entframework.kernel.system.modular.theme.service" })
@EntityScan("io.entframework.kernel.system.modular.theme.entity")
public class EntSysThemeAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SysThemeService.class)
	public SysThemeService sysThemeService() {
		return new SysThemeServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(SysThemeTemplateService.class)
	public SysThemeTemplateService sysThemeTemplateService() {
		return new SysThemeTemplateServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(SysThemeTemplateFieldService.class)
	public SysThemeTemplateFieldService sysThemeTemplateFieldService() {
		return new SysThemeTemplateFieldServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(SysThemeTemplateRelService.class)
	public SysThemeTemplateRelService sysThemeTemplateRelService() {
		return new SysThemeTemplateRelServiceImpl();
	}

}