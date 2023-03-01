package io.entframework.kernel.sms.modular.config;

import io.entframework.kernel.sms.modular.service.SysSmsService;
import io.entframework.kernel.sms.modular.service.impl.SysSmsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.sms.modular.controller",
		"io.entframework.kernel.sms.modular.converter", "io.entframework.kernel.sms.modular.service" })
@EntityScan("io.entframework.kernel.sms.modular.entity")
public class EntSmsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SysSmsService.class)
	public SysSmsService sysSmsService() {
		return new SysSmsServiceImpl();
	}

}