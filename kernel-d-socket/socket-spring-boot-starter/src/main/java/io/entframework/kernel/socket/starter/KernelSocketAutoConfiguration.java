/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.socket.starter;

import io.entframework.kernel.socket.api.SocketOperatorApi;
import io.entframework.kernel.socket.business.websocket.operator.WebSocketOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Socket的自动配置类
 *
 * @date 2021/6/2 下午5:48
 */
@Configuration
public class KernelSocketAutoConfiguration {

	/**
	 * Socket操作实现类
	 *
	 * @date 2021/6/2 下午5:48
	 **/
	@Bean
	@ConditionalOnMissingBean(SocketOperatorApi.class)
	public SocketOperatorApi socketOperatorApi() {
		return new WebSocketOperator();
	}

}
