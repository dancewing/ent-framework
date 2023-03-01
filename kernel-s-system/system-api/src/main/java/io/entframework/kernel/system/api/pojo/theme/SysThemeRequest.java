/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.theme;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 系统主题参数
 *
 * @date 2021/12/17 16:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysThemeRequest extends BaseRequest {

	/**
	 * 主键ID
	 */
	@NotNull(message = "主键ID不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
	@ChineseDescription("主键ID")
	private Long themeId;

	/**
	 * 主题名称
	 */
	@NotBlank(message = "主题名称不能为空", groups = { add.class, update.class })
	@ChineseDescription("主题名称")
	private String themeName;

	/**
	 * 主题属性(JSON格式)
	 */
	@NotBlank(message = "主题属性不能为空", groups = { add.class, update.class })
	@ChineseDescription("主题属性")
	private String themeValue;

	/**
	 * 主题模板ID
	 */
	@NotNull(message = "主题模板ID不能为空", groups = { add.class, update.class })
	@ChineseDescription("主题模板ID")
	private Long templateId;

}
