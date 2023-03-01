/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.starter;

import io.entframework.kernel.pay.alipay.service.impl.AlipayServiceImpl;
import io.entframework.kernel.pay.api.PayApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付模块自动配置
 *
 * @date 2021/05/29 21:38
 */
@Configuration
public class KernelPayAutoConfiguration {

	/**
	 * 支付 阿里支付实现
	 *
	 * @date 2021/05/29 21:38
	 */
	@Bean
	public PayApi payApi() {
		return new AlipayServiceImpl();
	}

}
