/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.system.api.RoleServiceApi;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.modular.entity.SysRole;

import java.util.List;

/**
 * 系统角色service接口
 *
 * @date 2020/11/5 上午11:12
 */
public interface SysRoleService extends BaseService<SysRoleRequest, SysRoleResponse, SysRole>, RoleServiceApi {

	/**
	 * 添加系统角色
	 *
	 * @param sysRoleRequest 添加参数
	 * @date 2020/11/5 上午11:13
	 */
	void add(SysRoleRequest sysRoleRequest);

	/**
	 * 删除系统角色
	 *
	 * @param sysRoleRequest 删除参数
	 * @date 2020/11/5 上午11:14
	 */
	void del(SysRoleRequest sysRoleRequest);

	/**
	 * 编辑系统角色
	 *
	 * @param sysRoleRequest 编辑参数
	 * @date 2020/11/5 上午11:14
	 */
	SysRoleResponse update(SysRoleRequest sysRoleRequest);

	/**
	 * 更新状态
	 *
	 * @param sysRoleRequest
	 */
	void updateStatus(SysRoleRequest sysRoleRequest);

	/**
	 * 查看系统角色
	 *
	 * @param sysRoleRequest 查看参数
	 * @return 系统角色
	 * @date 2020/11/5 上午11:14
	 */
	SysRoleResponse detail(SysRoleRequest sysRoleRequest);

	/**
	 * 查询系统角色
	 *
	 * @param sysRoleRequest 查询参数
	 * @return 查询分页结果
	 * @date 2020/11/5 上午11:13
	 */
	PageResult<SysRoleResponse> findPage(SysRoleRequest sysRoleRequest);

	/**
	 * 根据角色名模糊搜索系统角色列表
	 *
	 * @param sysRoleRequest 查询参数
	 * @return 增强版hashMap，格式：[{"id":456, "name":"总经理(zjl)"}]
	 * @date 2020/11/5 上午11:13
	 */
	List<SimpleDict> findList(SysRoleRequest sysRoleRequest);

	/**
	 * 授权菜单和按钮
	 *
	 * @date 2021/1/9 18:13
	 */
	void grantMenu(SysRoleRequest sysRoleMenuButtonRequest);

	/**
	 * 授权数据范围（组织机构）
	 *
	 * @param sysRoleRequest 授权参数
	 * @date 2020/11/5 上午11:14
	 */
	void grantDataScope(SysRoleRequest sysRoleRequest);

	/**
	 * 系统角色下拉（用于授权角色时选择）
	 *
	 * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
	 * @date 2020/11/5 上午11:13
	 */
	List<SimpleDict> dropDown();

	/***
	 * 查询角色拥有数据
	 *
	 * @param sysRoleRequest 查询参数
	 * @return 数据范围id集合
	 *
	 * @date 2020/11/5 上午11:15
	 */
	List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest);

	/**
	 * 获取角色绑定的资源树列表，用于分配接口权限
	 *
	 * @param roleId        角色id
	 * @param treeBuildFlag true-带树形结构，false-不组装树形结构的
	 * @date 2021/1/9 15:08
	 */
	List<ResourceTreeNode> getRoleResourceTree(Long roleId, Boolean treeBuildFlag);
}
