package io.entframework.kernel.file.modular.config;

import io.entframework.kernel.file.modular.service.SysFileInfoService;
import io.entframework.kernel.file.modular.service.SysFileStorageService;
import io.entframework.kernel.file.modular.service.impl.SysFileInfoServiceImpl;
import io.entframework.kernel.file.modular.service.impl.SysFileStorageServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.file.modular.controller",
		"io.entframework.kernel.file.modular.converter", "io.entframework.kernel.file.modular.service" })
@EntityScan("io.entframework.kernel.file.modular.entity")
public class EntFileAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SysFileInfoService.class)
	public SysFileInfoService sysFileInfoService() {
		return new SysFileInfoServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(SysFileStorageService.class)
	public SysFileStorageService sysFileStorageService() {
		return new SysFileStorageServiceImpl();
	}

}