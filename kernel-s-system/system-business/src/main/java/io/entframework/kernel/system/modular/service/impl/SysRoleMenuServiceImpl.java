/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.db.mybatis.mapper.GeneralMapperSupport;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;
import io.entframework.kernel.rule.tree.antd.TreeDataItem;
import io.entframework.kernel.rule.util.CheckedKeysUtils;
import io.entframework.kernel.system.api.pojo.request.SysRoleMenuRequest;
import io.entframework.kernel.system.api.pojo.response.SysMenuResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.modular.entity.SysMenu;
import io.entframework.kernel.system.modular.entity.SysMenuDynamicSqlSupport;
import io.entframework.kernel.system.modular.entity.SysRoleMenu;
import io.entframework.kernel.system.modular.entity.SysRoleMenuDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysRoleMenuService;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联信息
 *
 * @date 2020/12/19 18:21
 */
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuRequest, SysRoleMenuResponse, SysRoleMenu> implements SysRoleMenuService {

	@Resource
	private GeneralMapperSupport generalMapperSupport;

	public SysRoleMenuServiceImpl() {
		super(SysRoleMenuRequest.class, SysRoleMenuResponse.class, SysRoleMenu.class);
	}

	@Override
	public CheckedKeys<Long> getRoleMenuCheckedKeys(List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return new CheckedKeys<>();
		}
		List<SysMenuResponse> roleMenus = this.getRoleMenus(roleIds);
		List<TreeDataItem<Long>> dataItems = roleMenus.stream()
				.map((sysMenuResponse -> new TreeDataItem<>(sysMenuResponse.getMenuId(),
						sysMenuResponse.getMenuParentId(), sysMenuResponse.getMenuPids())))
				.toList();
		return CheckedKeysUtils.convert(dataItems);
	}

	private List<SysMenuResponse> getRoleMenus(List<Long> roleIds) {
		SelectStatementProvider selectStatement = SqlBuilder.select(SysMenuDynamicSqlSupport.selectList)
				.from(SysMenuDynamicSqlSupport.sysMenu)
				.join(SysRoleMenuDynamicSqlSupport.sysRoleMenu)
				.on(SysMenuDynamicSqlSupport.menuId, SqlBuilder.equalTo(SysRoleMenuDynamicSqlSupport.menuId))
				.where(SysRoleMenuDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIds))
				.build().render(RenderingStrategies.MYBATIS3);
		List<SysMenu> sysMenus = generalMapperSupport.selectMany(selectStatement);
		return sysMenus.stream().map(sysMenu -> this.converterService.convert(sysMenus, SysMenuResponse.class)).toList();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByRoleId(Long roleId) {
		this.getRepository().delete(getEntityClass(), c -> c.where(SysRoleMenuDynamicSqlSupport.roleId,
				SqlBuilder.isEqualTo(roleId)));
	}

	@Override
	public List<SysRoleMenu> getRoleMenus(Collection<Long> roleIds) {
		return this.getRepository().select(getEntityClass(), c -> c.where(SysRoleMenuDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIds)));
	}

	@Override
	public List<SysRoleMenu> batchCreateEntity(List<SysRoleMenu> records) {
		return this.getRepository().insertMultiple(records);
	}
}
