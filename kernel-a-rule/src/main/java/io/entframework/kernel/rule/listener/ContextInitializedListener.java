/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * context初始化的监听器
 *
 * @author jeff_qian
 * @date 2021/5/14 20:28
 */
@Slf4j
public abstract class ContextInitializedListener implements ApplicationListener<ApplicationContextInitializedEvent> {

	/**
	 * Spring Event 触发顺序 ApplicationStartingEvent //启动开始的时候执行的事件
	 * ApplicationEnvironmentPreparedEvent //上下文创建之前运行的事件
	 * ApplicationContextInitializedEvent // ApplicationPreparedEvent
	 * //上下文创建完成，注入的bean还没加载完成 ContextRefreshedEvent //上下文刷新
	 * ServletWebServerInitializedEvent //web服务器初始化 ApplicationStartedEvent//
	 * ApplicationReadyEvent //启动成功 ApplicationFailedEvent//在启动Spring发生异常时触发
	 * @param event
	 */
	@Override
	public void onApplicationEvent(ApplicationContextInitializedEvent event) {

		// 如果是配置中心的上下文略过，spring cloud环境environment会读取不到
		ConfigurableApplicationContext applicationContext = event.getApplicationContext();
		if (applicationContext instanceof AnnotationConfigApplicationContext) {
			return;
		}

		// 执行具体业务
		this.eventCallback(event);
	}

	/**
	 * 监听器具体的业务逻辑
	 *
	 * @date 2021/5/14 20:17
	 */
	public abstract void eventCallback(ApplicationContextInitializedEvent event);

}
