/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import io.entframework.kernel.db.dao.repository.GeneralRepository;
import io.entframework.kernel.rule.constants.SymbolConstant;
import io.entframework.kernel.system.api.MenuServiceApi;
import io.entframework.kernel.system.api.RoleServiceApi;
import io.entframework.kernel.system.modular.entity.SysMenu;
import io.entframework.kernel.system.modular.entity.SysMenuDynamicSqlSupport;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuServiceApi {

	@Resource
	private GeneralRepository generalRepository;

	@Resource
	private RoleServiceApi roleServiceApi;

	@Override
	public boolean hasMenu(Long appId) {
		return generalRepository.count(SysMenu.class,
				c -> c.where(SysMenuDynamicSqlSupport.appId, SqlBuilder.isEqualTo(appId))) > 0L;
	}

	@Override
	public List<Long> getUserAppIdList() {
		// 非超级管理员获取自己的菜单
		List<SysMenu> list;
		if (!LoginContext.me().getSuperAdminFlag()) {
			List<Long> currentUserMenuIds = this.getCurrentUserMenuIds();
			list = this.generalRepository.select(SysMenu.class,
					c -> c.where(SysMenuDynamicSqlSupport.menuId, SqlBuilder.isIn(currentUserMenuIds))
							.groupBy(SysMenuDynamicSqlSupport.appId));
		}
		else {
			list = this.generalRepository.select(SysMenu.class, c -> c.groupBy(SysMenuDynamicSqlSupport.appId));
		}
		return list.stream().map(SysMenu::getAppId).toList();
	}

	@Override
	public Set<Long> getMenuAllParentMenuId(Set<Long> menuIds) {
		Set<Long> parentMenuIds = new HashSet<>();

		// 查询所有菜单信息
		List<SysMenu> sysMenus = this.generalRepository.select(SysMenu.class,
				c -> c.where(SysMenuDynamicSqlSupport.menuId, SqlBuilder.isIn(menuIds)));
		if (ObjectUtil.isEmpty(sysMenus)) {
			return parentMenuIds;
		}

		// 获取所有父菜单ID
		for (SysMenu sysMenu : sysMenus) {
			String menuPids = sysMenu.getMenuPids().replace("\\[", "").replace("\\]", "");
			String[] ids = menuPids.split(SymbolConstant.COMMA);
			for (String id : ids) {
				parentMenuIds.add(Long.parseLong(id));
			}
		}

		return parentMenuIds;
	}

	private List<Long> getCurrentUserMenuIds() {

		// 获取当前用户的角色id集合
		LoginUser loginUser = LoginContext.me().getLoginUser();
		List<Long> roleIdList = loginUser.getSimpleRoleInfoList().stream().map(SimpleRoleInfo::getRoleId).toList();

		// 当前用户角色为空，则没菜单
		if (ObjectUtil.isEmpty(roleIdList)) {
			return CollUtil.newArrayList();
		}

		// 获取角色拥有的菜单id集合
		List<Long> menuIdList = roleServiceApi.getMenuIdsByRoleIds(roleIdList);
		if (ObjectUtil.isEmpty(menuIdList)) {
			return CollUtil.newArrayList();
		}

		return menuIdList;
	}

}
