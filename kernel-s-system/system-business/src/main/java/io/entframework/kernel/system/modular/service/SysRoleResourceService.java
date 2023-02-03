/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import java.util.List;

import io.entframework.kernel.db.mds.service.BaseService;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleResourceResponse;
import io.entframework.kernel.system.modular.entity.SysRoleResource;

/**
 * 系统角色菜单service接口
 *
 * @date 2020/11/5 上午11:17
 */
public interface SysRoleResourceService extends BaseService<SysRoleResourceRequest, SysRoleResourceResponse, SysRoleResource> {

	/**
	 * 授权资源
	 *
	 * @param sysRoleRequest 授权参数
	 * @date 2020/11/5 上午11:17
	 */
	void grantResource(SysRoleRequest sysRoleRequest);

	/**
	 * 根据角色id删除对应的角色资源信息
	 *
	 * @param roleId 角色id
	 * @date 2020/11/5 上午11:18
	 */
	void deleteRoleResourceListByRoleId(Long roleId);

	/**
	 * 获取角色已分配资源Code
	 *
	 * @param roleIdList
	 * @return
	 */
	List<String> getRoleResourceCodes(List<Long> roleIdList);

	List<SysRoleResourceResponse> getRoleResources(List<Long> roleIdList);
}
