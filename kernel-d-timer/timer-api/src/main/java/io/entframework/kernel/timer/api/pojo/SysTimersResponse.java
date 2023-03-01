/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.timer.api.pojo;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.timer.api.enums.TimerJobStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 定时任务 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysTimersResponse extends BaseResponse {

	/**
	 * 定时器id
	 */
	@ChineseDescription("定时器id")
	private Long timerId;

	/**
	 * 任务名称
	 */
	@ChineseDescription("任务名称")
	private String timerName;

	/**
	 * 执行任务的class的类名（实现了TimerAction接口的类的全称）
	 */
	@ChineseDescription("执行任务的class的类名（实现了TimerAction接口的类的全称）")
	private String actionClass;

	/**
	 * 定时任务表达式
	 */
	@ChineseDescription("定时任务表达式")
	private String cron;

	/**
	 * 参数
	 */
	@ChineseDescription("参数")
	private String params;

	/**
	 * 状态：1-运行，2-停止
	 */
	@ChineseDescription("状态：1-运行，2-停止")
	private TimerJobStatusEnum jobStatus;

	/**
	 * 备注信息
	 */
	@ChineseDescription("备注信息")
	private String remark;

	/**
	 * 是否删除：Y-被删除，N-未删除
	 */
	@ChineseDescription("是否删除：Y-被删除，N-未删除")
	private YesOrNotEnum delFlag;

}