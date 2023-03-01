/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.starter;

import io.entframework.kernel.timer.api.TimerExeService;
import io.entframework.kernel.timer.hutool.HutoolTimerExeServiceImpl;
import io.entframework.kernel.timer.modular.listener.TaskRunListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务的自动配置
 *
 * @date 2020/12/1 21:34
 */
@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.timer" })
public class KernelTimerAutoConfiguration {

	/**
	 * hutool的定时任务
	 *
	 * @date 2020/12/1 21:18
	 */
	@Bean
	@ConditionalOnMissingBean(TimerExeService.class)
	public TimerExeService timerExeService() {
		return new HutoolTimerExeServiceImpl();
	}

	@Bean
	public TaskRunListener taskRunListener() {
		return new TaskRunListener();
	}

}
