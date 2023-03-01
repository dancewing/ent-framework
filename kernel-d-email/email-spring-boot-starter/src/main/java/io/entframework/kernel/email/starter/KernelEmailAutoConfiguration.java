/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.starter;

import io.entframework.kernel.email.api.MailSenderApi;
import io.entframework.kernel.email.api.config.EmailProperties;
import io.entframework.kernel.email.jdk.JavaMailSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件发送的自动配置类
 *
 * @date 2020/12/1 11:25
 */
@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class KernelEmailAutoConfiguration {

	/**
	 * java mail方式发送邮件的接口
	 *
	 * @date 2020/12/1 11:32
	 */
	@Bean
	@ConditionalOnMissingBean(MailSenderApi.class)
	public MailSenderApi mailSenderApi() {
		return new JavaMailSender();
	}

}
