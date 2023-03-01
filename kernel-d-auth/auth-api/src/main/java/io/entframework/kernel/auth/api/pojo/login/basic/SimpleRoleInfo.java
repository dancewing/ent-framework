/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.pojo.login.basic;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户基本信息
 *
 * @date 2020/12/26 18:14
 */
@Data
public class SimpleRoleInfo implements Serializable {

	/**
	 * 主键
	 */
	private Long roleId;

	/**
	 * 名称
	 */
	private String roleName;

	/**
	 * 编码
	 */
	private String roleCode;

}
