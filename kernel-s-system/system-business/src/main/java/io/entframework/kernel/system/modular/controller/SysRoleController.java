/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import java.util.List;

import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import jakarta.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.modular.service.SysRoleMenuService;
import io.entframework.kernel.system.modular.service.SysRoleResourceService;
import io.entframework.kernel.system.modular.service.SysRoleService;

import cn.hutool.core.collection.ListUtil;

/**
 * 系统角色控制器
 *
 * @date 2020/11/5 上午10:19
 */
@RestController
@ApiResource(name = "系统角色管理")
public class SysRoleController {

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysRoleResourceService sysRoleResourceService;

	@Resource
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 添加系统角色
	 *
	 * @date 2020/11/5 上午10:38
	 */
	@PostResource(name = "添加角色", path = "/sys-role/add")
	public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysRoleRequest sysRoleRequest) {
		sysRoleService.add(sysRoleRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除系统角色
	 *
	 * @date 2020/11/5 上午10:48
	 */
	@PostResource(name = "角色删除", path = "/sys-role/delete")
	public ResponseData<Void> delete(@RequestBody @Validated(BaseRequest.delete.class) SysRoleRequest sysRoleRequest) {
		sysRoleService.del(sysRoleRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 编辑系统角色
	 *
	 * @date 2020/11/5 上午10:49
	 */
	@PostResource(name = "更新角色", path = "/sys-role/update")
	public ResponseData<Void> update(@RequestBody @Validated(BaseRequest.update.class) SysRoleRequest sysRoleRequest) {
		sysRoleService.update(sysRoleRequest);
		return ResponseData.OK_VOID;
	}

	@PostResource(name = "更新角色状态", path = "/sys-role/update-status")
	public ResponseData<Void> updateStatus(
			@RequestBody @Validated(BaseRequest.updateStatus.class) SysRoleRequest sysRoleRequest) {
		sysRoleService.updateStatus(sysRoleRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查看系统角色
	 *
	 * @date 2020/11/5 上午10:50
	 */
	@GetResource(name = "角色查看", path = "/sys-role/detail")
	public ResponseData<SysRoleResponse> detail(@Validated(BaseRequest.detail.class) SysRoleRequest sysRoleRequest) {
		return ResponseData.ok(sysRoleService.detail(sysRoleRequest));
	}

	/**
	 * 查询系统角色
	 *
	 * @date 2020/11/5 上午10:19
	 */
	@GetResource(name = "查询角色", path = "/sys-role/page")
	public ResponseData<PageResult<SysRoleResponse>> page(SysRoleRequest sysRoleRequest) {
		return ResponseData.ok(sysRoleService.findPage(sysRoleRequest));
	}

	/**
	 * 角色授权菜单
	 *
	 * @date 2021/1/9 18:04
	 */
	@PostResource(name = "角色授权菜单和按钮", path = "/sys-role/grant-menu")
	public ResponseData<Void> grantMenu(
			@RequestBody @Validated(SysRoleRequest.grantMenu.class) SysRoleRequest sysRoleRequest) {
		sysRoleService.grantMenu(sysRoleRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 设置角色绑定的数据范围类型和数据范围
	 *
	 * @date 2020/3/28 16:05
	 */
	@PostResource(name = "设置角色绑定的数据范围类型和数据范围", path = "/sys-role/grant-data-scope")
	public ResponseData<Void> grantData(
			@RequestBody @Validated(SysRoleRequest.grantDataScope.class) SysRoleRequest sysRoleParam) {
		sysRoleService.grantDataScope(sysRoleParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 系统角色下拉（用于用户授权角色时选择）
	 *
	 * @date 2020/11/6 13:49
	 */
	@GetResource(name = "角色下拉", path = "/sys-role/drop-down")
	public ResponseData<List<SimpleDict>> dropDown() {
		return ResponseData.ok(sysRoleService.dropDown());
	}

	/**
	 * 拥有菜单
	 *
	 * @date 2020/11/5 上午10:58
	 */
	@GetResource(name = "角色拥有菜单", path = "/sys-role/get-role-menus")
	public ResponseData<CheckedKeys<Long>> getRoleMenus(
			@Validated(BaseRequest.detail.class) SysRoleRequest sysRoleRequest) {
		Long roleId = sysRoleRequest.getRoleId();
		return ResponseData.ok(sysRoleMenuService.getRoleMenuCheckedKeys(ListUtil.toList(roleId)));
	}

	/**
	 * 拥有数据
	 *
	 * @date 2020/11/5 上午10:59
	 */
	@GetResource(name = "角色拥有数据", path = "/sys-role/get-role-data-scope")
	public ResponseData<List<Long>> getRoleDataScope(
			@Validated(BaseRequest.detail.class) SysRoleRequest sysRoleRequest) {
		return ResponseData.ok(sysRoleService.getRoleDataScope(sysRoleRequest));
	}

	/**
	 * 获取资源树列表，用于角色分配接口权限
	 *
	 * @date 2021/1/9 15:07
	 */
	@GetResource(name = "获取资源树列表，用于角色分配接口权限", path = "/sys-role/get-resource-tree")
	public ResponseData<List<ResourceTreeNode>> getRoleResourceTree(SysRoleRequest sysRoleRequest) {
		List<ResourceTreeNode> resourceLateralTree = sysRoleService.getRoleResourceTree(sysRoleRequest.getRoleId(),
				true);
		return ResponseData.ok(resourceLateralTree);
	}

	@GetResource(name = "获取已分配资源列表", path = "/sys-role/get-selected-resource")
	public ResponseData<List<String>> getRoleResourceCodes(SysRoleRequest sysRoleRequest) {
		List<String> resourceLateralTree = sysRoleResourceService
				.getRoleResourceCodes(ListUtil.toList(sysRoleRequest.getRoleId()));
		return ResponseData.ok(resourceLateralTree);
	}

	/**
	 * 角色授权资源
	 *
	 * @date 2020/11/22 19:51
	 */
	@PostResource(name = "角色授权资源", path = "/sys-role/grant-resource")
	public ResponseData<Void> grantResource(
			@RequestBody @Validated(SysRoleRequest.grantResource.class) SysRoleRequest sysRoleParam) {
		sysRoleResourceService.grantResource(sysRoleParam);
		return ResponseData.OK_VOID;
	}

}
