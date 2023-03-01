/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.rule.constants.TreeConstants;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import io.entframework.kernel.system.api.AppServiceApi;
import io.entframework.kernel.system.api.pojo.menu.MenuMetaDTO;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;
import io.entframework.kernel.system.api.pojo.menu.AntdMenuDTO;
import io.entframework.kernel.system.api.pojo.menu.MenuSelectTreeNode;
import io.entframework.kernel.system.api.pojo.menu.MenuTreeResponse;
import io.entframework.kernel.system.api.pojo.response.SysMenuResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.modular.entity.SysMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对于antd vue版本的前端菜单的组装
 *
 * @date 2020/12/30 20:11
 */
public class MenusFactory {

	private MenusFactory() {
	}

	/**
	 * 组装antdv用的获取所有菜单列表详情
	 * @param appSortedMenus 按应用排序过的菜单集合
	 * @date 2021/1/7 18:17
	 */
	public static List<AntdMenuDTO> createTotalMenus(Map<Long, List<SysMenuResponse>> appSortedMenus, Long activeAppId,
			boolean createTop) {

		// 创建应用级别的菜单集合
		ArrayList<AntdMenuDTO> appSortedAntdMenus = new ArrayList<>();

		// 如果用户菜单中包含了激活的应用，先放激活的应用的
		if (appSortedMenus.containsKey(activeAppId)) {

			List<SysMenuResponse> treeStructMenu = new DefaultTreeBuildFactory<SysMenuResponse>(
					TreeConstants.DEFAULT_PARENT_ID.toString()).doTreeBuild(appSortedMenus.get(activeAppId));
			List<AntdMenuDTO> antdMenuDTOS = doModelTransfer(treeStructMenu);
			if (createTop) {
				// 创建顶层应用菜单
				AntdMenuDTO firstSortApp = createRootAppMenu(activeAppId);
				// 更新顶层应用级别的菜单
				firstSortApp.setChildren(antdMenuDTOS);
				appSortedAntdMenus.add(firstSortApp);
			}
			else {
				appSortedAntdMenus.addAll(antdMenuDTOS);
			}
		}

		// 创建其他应用的菜单
		for (Map.Entry<Long, List<SysMenuResponse>> entry : appSortedMenus.entrySet()) {
			if (!entry.getKey().equals(activeAppId)) {

				List<SysMenuResponse> treeStructMenu = new DefaultTreeBuildFactory<SysMenuResponse>(
						TreeConstants.DEFAULT_PARENT_ID.toString()).doTreeBuild(entry.getValue());
				List<AntdMenuDTO> antdMenuDTOS = doModelTransfer(treeStructMenu);

				if (createTop) {
					// 创建顶层应用菜单
					AntdMenuDTO rootAppMenu = createRootAppMenu(entry.getKey());
					// 更新顶层应用级别的菜单
					rootAppMenu.setChildren(antdMenuDTOS);
					appSortedAntdMenus.add(rootAppMenu);
				}
				else {
					appSortedAntdMenus.addAll(antdMenuDTOS);
				}

			}
		}

		return appSortedAntdMenus;
	}

	/**
	 * menu实体转化为菜单树节点
	 *
	 * @date 2020/11/23 21:54
	 */
	public static MenuSelectTreeNode parseMenuBaseTreeNode(SysMenuResponse sysMenu) {
		MenuSelectTreeNode menuTreeNode = new MenuSelectTreeNode();
		menuTreeNode.setId(sysMenu.getMenuId());
		menuTreeNode.setParentId(sysMenu.getMenuParentId());
		menuTreeNode.setValue(String.valueOf(sysMenu.getMenuId()));
		menuTreeNode.setTitle(sysMenu.getMenuName());
		menuTreeNode.setWeight(sysMenu.getMenuSort());
		return menuTreeNode;
	}

	/**
	 * 添加根节点
	 *
	 * @date 2021/4/16 15:52
	 */
	public static MenuSelectTreeNode createRootNode() {
		MenuSelectTreeNode menuSelectTreeNode = new MenuSelectTreeNode();
		menuSelectTreeNode.setId(-1L);
		menuSelectTreeNode.setParentId(-2L);
		menuSelectTreeNode.setTitle("根节点");
		menuSelectTreeNode.setValue(String.valueOf(menuSelectTreeNode.getId()));
		menuSelectTreeNode.setWeight(new BigDecimal(-1));
		return menuSelectTreeNode;
	}

	/**
	 * 填充叶子节点的标识
	 *
	 * @date 2021/8/8 15:22
	 */
	public static void fillLeafFlag(List<SysMenuResponse> sysMenuList) {
		for (SysMenuResponse sysMenu : sysMenuList) {
			sysMenu.setLeafFlag(true);

			// 判断这个节点下面有没有节点
			for (SysMenuResponse tempMenu : sysMenuList) {
				if (tempMenu.getMenuPids().contains("[" + sysMenu.getMenuId() + "]")) {
					sysMenu.setLeafFlag(false);
				}
			}
		}
	}

	/**
	 * 菜单集合转化成角色分配菜单的集合
	 *
	 * @date 2021/8/10 22:56
	 */
	public static List<MenuTreeResponse> parseMenuAndButtonTreeResponse(List<SysMenu> sysMenuList,
			List<SysRoleMenuResponse> roleBindMenus) {
		ArrayList<MenuTreeResponse> result = new ArrayList<>();

		if (ObjectUtil.isEmpty(sysMenuList)) {
			return result;
		}

		for (SysMenu sysMenu : sysMenuList) {
			MenuTreeResponse menuTreeResponse = new MenuTreeResponse();
			menuTreeResponse.setId(sysMenu.getMenuId());
			menuTreeResponse.setName(sysMenu.getMenuName());
			menuTreeResponse.setCode(sysMenu.getMenuCode());
			menuTreeResponse.setPid(sysMenu.getMenuParentId());
			menuTreeResponse.setChecked(false);

			if (ObjectUtil.isNotEmpty(roleBindMenus)) {
				for (SysRoleMenuResponse roleBindMenu : roleBindMenus) {
					if (roleBindMenu.getMenuId().equals(sysMenu.getMenuId())) {
						menuTreeResponse.setChecked(true);
					}
				}
			}

			result.add(menuTreeResponse);
		}

		return result;
	}

	/**
	 * 获取分类过的用户菜单，返回一个menus数组，并且第一个是激活的应用
	 *
	 * @date 2021/8/24 16:50
	 */
	public static Map<Long, List<SysMenuResponse>> sortUserMenusByAppCode(List<SysMenuResponse> currentUserMenus) {

		// 根据应用编码分类的菜单，key是应用编码，value是菜单
		HashMap<Long, List<SysMenuResponse>> appMenus = new HashMap<>();

		// 将菜单分类
		for (SysMenuResponse currentUserMenu : currentUserMenus) {

			// 获取这个菜单的应用编码
			Long appCode = currentUserMenu.getAppId();

			// 获取该应用已有的菜单集合
			List<SysMenuResponse> sysMenus = appMenus.get(appCode);
			if (sysMenus == null) {
				sysMenus = new ArrayList<>();
			}

			sysMenus.add(currentUserMenu);
			appMenus.put(appCode, sysMenus);
		}

		return appMenus;
	}

	/**
	 * 模型转化
	 *
	 * @date 2021/3/23 21:40
	 */
	private static List<AntdMenuDTO> doModelTransfer(List<SysMenuResponse> sysMenuList) {
		if (ObjectUtil.isEmpty(sysMenuList)) {
			return null;
		}
		else {
			ArrayList<AntdMenuDTO> resultMenus = new ArrayList<>();

			for (SysMenuResponse sysMenu : sysMenuList) {
				AntdMenuDTO antdvMenuItem = new AntdMenuDTO();
				antdvMenuItem.setName(sysMenu.getMenuCode());
				antdvMenuItem.setIcon(sysMenu.getIcon());
				antdvMenuItem.setPath(sysMenu.getRouter());
				antdvMenuItem.setHide(YesOrNotEnum.N == sysMenu.getVisible());
				antdvMenuItem.setActive(sysMenu.getActiveUrl());
				antdvMenuItem.setMeta(doMetaTransfer(sysMenu));
				if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
					antdvMenuItem.setChildren(doModelTransfer(sysMenu.getChildren()));
				}
				resultMenus.add(antdvMenuItem);
			}

			return resultMenus;
		}
	}

	private static MenuMetaDTO doMetaTransfer(SysMenuResponse sysMenuResponse) {
		if (ObjectUtil.isEmpty(sysMenuResponse)) {
			return null;
		}
		else {
			MenuMetaDTO metaDTO = new MenuMetaDTO();
			metaDTO.setIcon(sysMenuResponse.getIcon());
			metaDTO.setTitle(sysMenuResponse.getMenuName());
			metaDTO.setOrderNo(sysMenuResponse.getMenuSort() != null ? sysMenuResponse.getMenuSort().intValue() : null);
			return metaDTO;
		}
	}

	/**
	 * 创建顶层应用层级的菜单
	 *
	 * @date 2021/8/24 17:23
	 */
	private static AntdMenuDTO createRootAppMenu(Long appId) {

		AntdMenuDTO antdMenuDTO = new AntdMenuDTO();

		// 获取应用的详细信息
		AppServiceApi appServiceApi = SpringUtil.getBean(AppServiceApi.class);
		SysAppResponse appInfoByAppCode = appServiceApi.getApp(appId);
		antdMenuDTO.setName(appInfoByAppCode.getAppName());
		antdMenuDTO.setIcon(appInfoByAppCode.getAppIcon());
		antdMenuDTO.setPath("/" + appInfoByAppCode.getAppCode());
		antdMenuDTO.setHide(false);
		antdMenuDTO.setActive(null);

		return antdMenuDTO;
	}

}
