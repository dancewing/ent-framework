/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.system.api.pojo.theme;

import java.util.Date;
import java.util.Map;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;

import lombok.Data;

/**
 * 用于前端数据渲染
 *
 * @date 2021/12/31 10:01
 */
@Data
public class SysThemeResponse {

	/**
	 * 主键ID
	 */
	@ChineseDescription("主键id")
	private Long themeId;

	/**
	 * 主题名称
	 */
	@ChineseDescription("主题名称")
	private String themeName;

	/**
	 * 主题属性(JSON格式)
	 */
	@ChineseDescription("主题属性(JSON格式)")
	private String themeValue;

	/**
	 * 主题模板ID
	 */
	@ChineseDescription("主题模板id")
	private Long templateId;

	/**
	 * 启用状态：Y-启用，N-禁用
	 */
	@ChineseDescription("启用状态：Y-启用，N-禁用")
	private YesOrNotEnum statusFlag;

	/**
	 * 模板名称，用于前端数据渲染
	 */
	@ChineseDescription("模板名称，用于前端数据渲染")
	private String templateName;

	/**
	 * 创建时间
	 */
	@ChineseDescription("创建时间")
	private Date createTime;

	/**
	 * 动态表单的key-value属性
	 */
	private transient Map<String, Object> dynamicForm;

	/**
	 * 用于编辑界面渲染antdv的临时文件展示
	 */
	private transient Map<String, AntdvFileInfo[]> tempFileList;

}
