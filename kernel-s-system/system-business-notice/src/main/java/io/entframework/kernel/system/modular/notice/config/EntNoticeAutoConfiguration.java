package io.entframework.kernel.system.modular.notice.config;

import io.entframework.kernel.system.modular.notice.service.SysNoticeService;
import io.entframework.kernel.system.modular.notice.service.impl.SysNoticeServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.system.modular.notice.controller",
		"io.entframework.kernel.system.modular.notice.converter",
		"io.entframework.kernel.system.modular.notice.service" })
@EntityScan("io.entframework.kernel.system.modular.notice.entity")
public class EntNoticeAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SysNoticeService.class)
	public SysNoticeService sysNoticeService() {
		return new SysNoticeServiceImpl();
	}

}