/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api;

import io.entframework.kernel.system.api.pojo.organization.DataScopeDTO;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;

import java.util.List;

/**
 * 数据范围的获取接口
 *
 * @date 2020/11/6 11:54
 */
public interface DataScopeApi {

	/**
	 * 获取用户的数据范围
	 * <p>
	 * 目前不考虑一个用户多角色多部门下的数据范围，只考虑一个用户只对应一个主部门下的数据范围
	 * <p>
	 * 此方法用在非超级管理员用户的获取数据范围
	 * @param userId 用户id
	 * @param sysRoles 角色信息
	 * @return 数据范围内容
	 * @date 2020/11/5 上午11:44
	 */
	DataScopeDTO getDataScope(Long userId, List<SysRoleResponse> sysRoles);

}
