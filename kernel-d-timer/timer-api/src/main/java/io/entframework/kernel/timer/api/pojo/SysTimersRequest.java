/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.api.pojo;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.timer.api.enums.TimerJobStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 定时任务
 *
 * @date 2020/6/30 18:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysTimersRequest extends BaseRequest {

	/**
	 * 定时器id
	 */
	@NotNull(message = "主键timerId不能为空",
			groups = { update.class, detail.class, delete.class, startTimer.class, stopTimer.class })
	private Long timerId;

	/**
	 * 任务名称
	 */
	@NotBlank(message = "任务名称不能为空", groups = { add.class, update.class })
	private String timerName;

	/**
	 * 执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）
	 */
	@NotBlank(message = "任务的class的类名不能为空", groups = { add.class, update.class })
	private String actionClass;

	/**
	 * 定时任务表达式
	 */
	@NotBlank(message = "定时任务表达式不能为空", groups = { add.class, update.class })
	private String cron;

	/**
	 * 状态（字典 1运行 2停止）
	 */
	@NotNull(message = "任务状态不能为空", groups = { update.class })
	private TimerJobStatusEnum jobStatus;

	/**
	 * 参数
	 */
	private String params;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 是否删除标记
	 */
	private YesOrNotEnum delFlag;

	/**
	 * 启用定时任务
	 */
	public @interface startTimer {

	}

	/**
	 * 停止定时任务
	 */
	public @interface stopTimer {

	}

}
