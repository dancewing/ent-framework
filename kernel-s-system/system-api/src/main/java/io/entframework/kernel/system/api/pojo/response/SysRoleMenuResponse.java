/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.response;

import lombok.Data;

/**
 * 角色菜单关联返回数据
 *
 * @date 2021/1/9 18:07
 */
@Data
public class SysRoleMenuResponse {

	/**
	 * 主键
	 */
	private Long roleMenuId;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 菜单id
	 */
	private Long menuId;

}
