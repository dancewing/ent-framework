/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 菜单资源绑定 服务响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysMenuResourceResponse extends BaseResponse {

	/**
	 * 主键
	 */
	@ChineseDescription("主键")
	private Long menuResourceId;

	/**
	 * 菜单或按钮id
	 */
	@ChineseDescription("菜单或按钮id")
	private Long menuId;

	/**
	 * 资源的编码
	 */
	@ChineseDescription("资源的编码")
	private String resourceCode;

}