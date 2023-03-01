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

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.menu.AntdMenuDTO;
import io.entframework.kernel.system.api.pojo.menu.MenuSelectTreeNode;
import io.entframework.kernel.system.api.pojo.menu.MenuTreeResponse;
import io.entframework.kernel.system.api.pojo.request.SysMenuRequest;
import io.entframework.kernel.system.api.pojo.request.SysMenuResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysMenuResponse;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.modular.service.SysMenuResourceService;
import io.entframework.kernel.system.modular.service.SysMenuService;

/**
 * 系统菜单控制器
 *
 * @date 2020/3/20 18:54
 */
@RestController
@ApiResource(name = "菜单管理")
public class SysMenuController {

	@Resource
	private SysMenuService sysMenuService;

	@Resource
	private SysMenuResourceService sysMenuResourceService;

	/**
	 * 添加系统菜单
	 *
	 * @date 2020/3/27 8:57
	 */
	@PostResource(name = "添加系统菜单", path = "/sys-menu/add")
	public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysMenuRequest sysMenuRequest) {
		sysMenuService.add(sysMenuRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除系统菜单
	 *
	 * @date 2020/3/27 8:58
	 */
	@PostResource(name = "删除系统菜单", path = "/sys-menu/delete")
	public ResponseData<Void> delete(@RequestBody @Validated(BaseRequest.delete.class) SysMenuRequest sysMenuRequest) {
		sysMenuService.del(sysMenuRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 更新系统菜单
	 *
	 * @date 2020/3/27 8:59
	 */
	@PostResource(name = "更新系统菜单", path = "/sys-menu/update")
	public ResponseData<Void> update(@RequestBody @Validated(BaseRequest.update.class) SysMenuRequest sysMenuRequest) {
		sysMenuService.update(sysMenuRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查看系统菜单
	 *
	 * @date 2020/3/27 9:01
	 */
	@GetResource(name = "查看系统菜单", path = "/sys-menu/detail")
	public ResponseData<SysMenuResponse> detail(@Validated(BaseRequest.detail.class) SysMenuRequest sysMenuRequest) {
		return ResponseData.ok(sysMenuService.detail(sysMenuRequest));
	}

	/**
	 * AntdVue版本--获取系统左侧菜单（适用于登录后获取左侧菜单）
	 *
	 * @date 2021/1/7 15:17
	 */
	@GetResource(name = "获取系统所有菜单（适用于登录后获取左侧菜单）", path = "/sys-menu/get-left-menus", requiredPermission = false)
	public ResponseData<List<AntdMenuDTO>> getLeftMenus(SysMenuRequest sysMenuRequest) {
		List<AntdMenuDTO> sysMenuResponses = sysMenuService.getLeftMenus(sysMenuRequest);
		return ResponseData.ok(sysMenuResponses);
	}

	/**
	 * AntdVue版本--菜单列表，带树形结构（菜单管理界面的列表用）
	 *
	 * @date 2020/3/20 21:23
	 */
	@GetResource(name = "系统菜单列表（树）", path = "/sys-menu/list")
	public ResponseData<List<SysMenuResponse>> list(SysMenuRequest sysMenuRequest) {
		return ResponseData.ok(sysMenuService.findListWithTreeStructure(sysMenuRequest));
	}

	/**
	 * AntdVue版本--获取系统菜单树，用于新增，编辑时选择上级节点（用在新增和编辑菜单选择上级菜单）
	 *
	 * @date 2020/3/27 15:55
	 */
	@GetResource(name = "获取系统菜单树，用于新增，编辑时选择上级节点", path = "/sys-menu/tree")
	public ResponseData<List<MenuSelectTreeNode>> tree(SysMenuRequest sysMenuRequest) {
		return ResponseData.ok(sysMenuService.tree(sysMenuRequest));
	}

	/**
	 * 新版角色分配菜单和按钮界面使用的接口
	 *
	 * @date 2021/8/10 22:21
	 */
	@GetResource(name = "新版角色分配菜单和按钮界面使用的接口（v2）", path = "/sys-menu/menu-and-button-tree-children-v2")
	public ResponseData<List<MenuTreeResponse>> menuAndButtonTreeChildrenV2(SysRoleRequest sysRoleRequest) {
		List<MenuTreeResponse> treeResponseList = sysMenuService.getRoleMenuAndButtons(sysRoleRequest);
		return ResponseData.ok(treeResponseList);
	}

	/**
	 * 获取菜单的资源分配树
	 *
	 * @date 2021/8/8 22:38
	 */
	@GetResource(name = "资源分配树", path = "/sys-menu/get-resource-tree")
	public ResponseData<List<ResourceTreeNode>> getMenuResourceTree(
			@Validated(value = BaseRequest.list.class) SysMenuResourceRequest sysMenuResourceRequest) {
		List<ResourceTreeNode> menuResourceTree = sysMenuResourceService
				.getMenuResourceTree(sysMenuResourceRequest.getMenuId());
		return ResponseData.ok(menuResourceTree);
	}

	/**
	 * 获取菜单的已分配资源
	 *
	 */
	@GetResource(name = "获取菜单的已分配资源", path = "/sys-menu/get-select-resource")
	public ResponseData<List<String>> getMenuResourceList(
			@Validated(value = BaseRequest.list.class) SysMenuResourceRequest sysMenuResourceRequest) {
		List<String> codes = sysMenuResourceService.getMenuResourceCodes(sysMenuResourceRequest.getMenuId());
		return ResponseData.ok(codes);
	}

	/**
	 * 设置菜单资源绑定
	 *
	 * @date 2021/8/10 11:55
	 */
	@PostResource(name = "设置菜单资源绑定", path = "/sys-menu/bind-resource")
	public ResponseData<Void> bindMenuResource(
			@RequestBody @Validated(value = BaseRequest.add.class) SysMenuResourceRequest sysMenuResourceRequest) {
		sysMenuResourceService.addMenuResourceBind(sysMenuResourceRequest);
		return ResponseData.OK_VOID;
	}

}
