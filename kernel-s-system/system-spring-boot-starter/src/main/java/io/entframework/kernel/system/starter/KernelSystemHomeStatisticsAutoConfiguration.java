/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.system.starter;

import io.entframework.kernel.system.modular.home.aop.InterfaceStatisticsAop;
import io.entframework.kernel.system.modular.home.timer.InterfaceStatisticsTimer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 首页统计数据的装载
 *
 * @date 2022/2/9 17:57
 */
@Configuration
public class KernelSystemHomeStatisticsAutoConfiguration {

	/**
	 * 接口统计的AOP
	 *
	 * @date 2022/2/9 14:00
	 */
	@Bean
	public InterfaceStatisticsAop interfaceStatisticsAspect() {
		return new InterfaceStatisticsAop();
	}

	/**
	 * 定时将统计数据存入数据库
	 *
	 * @date 2022/2/9 17:58
	 */
	@Bean
	public InterfaceStatisticsTimer interfaceStatisticsHolder() {
		return new InterfaceStatisticsTimer();
	}

}
