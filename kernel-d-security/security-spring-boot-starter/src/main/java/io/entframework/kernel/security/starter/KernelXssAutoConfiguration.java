/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.starter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import io.entframework.kernel.security.xss.XssFilter;
import io.entframework.kernel.security.xss.XssJacksonDeserializer;
import io.entframework.kernel.security.xss.config.XssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * XSS安全过滤器相关配置
 *
 * @author jeff_qian
 * @date 2021/1/13 23:05
 */
@Configuration
@EnableConfigurationProperties({ XssProperties.class })
public class KernelXssAutoConfiguration {

	/**
	 * XSS Filter过滤器，用来过滤param之类的传参
	 *
	 * @date 2021/1/13 23:09
	 */
	@Bean
	@ConditionalOnProperty(prefix = "kernel.security.xss", name = "enabled", havingValue = "true",
			matchIfMissing = true)
	public FilterRegistrationBean<XssFilter> xssFilterFilterRegistrationBean(XssProperties properties) {
		List<String> split = CharSequenceUtil.split(properties.getUrlPatterns(), ',');
		String[] urlPatterns = ArrayUtil.toArray(split, String.class);
		FilterRegistrationBean<XssFilter> xssFilterFilterRegistrationBean = new FilterRegistrationBean<>();
		xssFilterFilterRegistrationBean.setFilter(new XssFilter(properties));
		xssFilterFilterRegistrationBean.addUrlPatterns(urlPatterns);
		xssFilterFilterRegistrationBean.setName(XssFilter.FILTER_NAME);
		xssFilterFilterRegistrationBean.setOrder(HIGHEST_PRECEDENCE);
		return xssFilterFilterRegistrationBean;
	}

	/**
	 * XSS的json反序列化器，针对json的传参
	 *
	 * @date 2021/1/13 23:09
	 */
	@Bean
	@ConditionalOnProperty(prefix = "kernel.security.xss", name = "enabled", havingValue = "true",
			matchIfMissing = true)
	public Jackson2ObjectMapperBuilderCustomizer xssJackson2ObjectMapperBuilderCustomizer(XssProperties properties) {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.deserializerByType(String.class,
				new XssJacksonDeserializer(properties));
	}

}
