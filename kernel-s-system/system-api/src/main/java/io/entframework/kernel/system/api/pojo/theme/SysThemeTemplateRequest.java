/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.theme;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.TemplateTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统主题模板参数
 *
 * @date 2021/12/17 16:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysThemeTemplateRequest extends BaseRequest {

	/**
	 * 主键ID
	 */
	@NotNull(message = "主键ID不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
	@ChineseDescription("主键ID")
	private Long templateId;

	/**
	 * 主题模板名称
	 */
	@NotBlank(message = "主题模板名称不能为空", groups = { add.class, update.class })
	@ChineseDescription("主题模板名称")
	private String templateName;

	/**
	 * 主题模板编码
	 */
	@NotNull(message = "主题模板编码不能为空", groups = { add.class, update.class })
	@ChineseDescription("主题模板编码")
	private String templateCode;

	/**
	 * 主题模板类型：1-系统类型，2-业务类型
	 */
	@NotNull(message = "主题模板类型不能为空", groups = { add.class, update.class })
	@ChineseDescription("主题模板类型")
	private TemplateTypeEnum templateType;

	/**
	 * 主题模板启用状态：Y-启用，N-禁用
	 */
	@ChineseDescription("主题模板启用状态")
	private YesOrNotEnum statusFlag;
}
