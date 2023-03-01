/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.hutool;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.timer.api.TimerAction;
import io.entframework.kernel.timer.api.TimerExeService;
import io.entframework.kernel.timer.api.exception.TimerException;
import io.entframework.kernel.timer.api.exception.enums.TimerExceptionEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * hutool方式的定时任务执行
 *
 * @date 2020/10/27 14:05
 */
@Slf4j
public class HutoolTimerExeServiceImpl implements TimerExeService {

	private boolean timerStarted = false;

	@Override
	public void start() {

		// ApplicationStartedListener 会被触发两次导致启动失败，临时方案
		// 本身这个Timer实现的方式意义不大
		if (!timerStarted) {
			// 设置秒级别的启用
			CronUtil.setMatchSecond(true);
			// 启动定时器执行器
			CronUtil.start();
			timerStarted = true;
		}

	}

	@Override
	public void stop() {
		CronUtil.stop();
	}

	@Override
	public void startTimer(String taskId, String cron, String className, String params) {

		// 判断任务id是否为空
		if (CharSequenceUtil.isBlank(taskId)) {
			throw new TimerException(TimerExceptionEnum.PARAM_HAS_NULL, "taskId");
		}

		// 判断任务cron表达式是否为空
		if (CharSequenceUtil.isBlank(cron)) {
			throw new TimerException(TimerExceptionEnum.PARAM_HAS_NULL, "cron");
		}

		// 判断类名称是否为空
		if (CharSequenceUtil.isBlank(className)) {
			throw new TimerException(TimerExceptionEnum.PARAM_HAS_NULL, "className");
		}

		// 预加载类看是否存在此定时任务类
		try {
			Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new TimerException(TimerExceptionEnum.CLASS_NOT_FOUND, className);
		}

		// 定义hutool的任务
		Task task = () -> {
			try {
				TimerAction timerAction = (TimerAction) SpringUtil.getBean(Class.forName(className));
				timerAction.action(params);
			}
			catch (ClassNotFoundException e) {
				log.error("任务执行异常：{}", e.getMessage());
			}
		};

		// 开始执行任务
		CronUtil.schedule(taskId, cron, task);
	}

	@Override
	public void stopTimer(String taskId) {
		CronUtil.remove(taskId);
	}

}
