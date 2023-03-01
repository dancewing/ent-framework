/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.starter;

import io.entframework.kernel.monitor.system.holder.SystemHardwareInfoHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统监控的自动配置
 *
 * @date 2021/1/31 22:37
 */
@Configuration
public class KernelMonitorAutoConfiguration {

	/**
	 * 系统信息的holder，从这里获取系统信息
	 *
	 * @date 2021/2/1 20:44
	 */
	@Bean
	public SystemHardwareInfoHolder systemHardwareInfoHolder() {
		return new SystemHardwareInfoHolder();
	}

}
