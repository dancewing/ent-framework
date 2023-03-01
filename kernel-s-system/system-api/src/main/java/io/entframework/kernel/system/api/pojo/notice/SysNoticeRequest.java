/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.notice;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * 系统通知参数
 *
 * @date 2021/1/8 21:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNoticeRequest extends BaseRequest {

	/**
	 * 通知id
	 */
	@NotNull(message = "noticeId不能为空", groups = { update.class, delete.class, detail.class })
	@ChineseDescription("通知id")
	private Long noticeId;

	/**
	 * 通知标题
	 */
	@NotBlank(message = "通知标题不能为空", groups = { add.class, update.class })
	@ChineseDescription("通知标题")
	private String noticeTitle;

	/**
	 * 通知摘要
	 */
	@ChineseDescription("通知摘要")
	private String noticeSummary;

	/**
	 * 通知优先级
	 */
	@ChineseDescription("通知优先级")
	private String priorityLevel;

	/**
	 * 通知开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ChineseDescription("通知开始时间")
	private LocalDateTime noticeBeginTime;

	/**
	 * 通知结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ChineseDescription("通知结束时间")
	private LocalDateTime noticeEndTime;

	/**
	 * 通知内容
	 */
	@ChineseDescription("通知内容")
	@NotBlank(message = "通知内容不能为空", groups = { add.class, update.class })
	private String noticeContent;

	/**
	 * 通知范围
	 */
	@ChineseDescription("通知范围")
	private String noticeScope;

}
