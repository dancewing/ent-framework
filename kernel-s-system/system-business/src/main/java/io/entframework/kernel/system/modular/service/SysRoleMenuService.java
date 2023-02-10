/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;
import io.entframework.kernel.system.api.pojo.request.SysRoleMenuRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.modular.entity.SysRoleMenu;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联信息
 *
 * @date 2020/12/19 18:21
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuRequest, SysRoleMenuResponse, SysRoleMenu> {
	CheckedKeys<Long> getRoleMenuCheckedKeys(List<Long> roleIds);

	void deleteByRoleId(Long roleId);

	List<SysRoleMenu> getRoleMenus(Collection<Long> roleIds);

	List<SysRoleMenu> batchCreateEntity(List<SysRoleMenu> request);
}
