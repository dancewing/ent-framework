/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.pojo.request.SysRoleDataScopeRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleDataScopeResponse;
import io.entframework.kernel.system.modular.entity.SysRoleDataScope;

import java.util.List;

/**
 * 系统角色数据范围service接口
 *
 * @date 2020/11/5 上午11:21
 */
public interface SysRoleDataScopeService
		extends BaseService<SysRoleDataScopeRequest, SysRoleDataScopeResponse, SysRoleDataScope> {

	/**
	 * 授权数据
	 * @param sysRoleRequest 授权参数
	 * @date 2020/11/5 上午11:20
	 */
	void grantDataScope(SysRoleRequest sysRoleRequest);

	/**
	 * 根据角色id获取角色数据范围集合
	 * @param roleIdList 角色id集合
	 * @return 数据范围id集合
	 * @date 2020/11/5 上午11:21
	 */
	List<Long> getRoleDataScopeIdList(List<Long> roleIdList);

	/**
	 * 新增
	 * @param sysRoleDataScopeRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	void add(SysRoleDataScopeRequest sysRoleDataScopeRequest);

	/**
	 * 删除
	 * @param sysRoleDataScopeRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	void del(SysRoleDataScopeRequest sysRoleDataScopeRequest);

	/**
	 * 根据角色id 删除角色数据范围
	 * @param roleId 角色id
	 * @date 2021/1/26 12:52
	 */
	void delByRoleId(Long roleId);

	/**
	 * 修改
	 * @param sysRoleDataScopeRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	SysRoleDataScopeResponse update(SysRoleDataScopeRequest sysRoleDataScopeRequest);

	/**
	 * 查询-详情
	 * @param sysRoleDataScopeRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	SysRoleDataScopeResponse detail(SysRoleDataScopeRequest sysRoleDataScopeRequest);

	/**
	 * 查询-列表
	 * @param sysRoleDataScopeRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	List<SysRoleDataScopeResponse> findList(SysRoleDataScopeRequest sysRoleDataScopeRequest);

}
