/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.system.api.RoleDataScopeServiceApi;
import io.entframework.kernel.system.api.pojo.request.SysRoleDataScopeRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleDataScopeResponse;
import io.entframework.kernel.system.modular.entity.SysRoleDataScope;
import io.entframework.kernel.system.modular.entity.SysRoleDataScopeDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysRoleDataScopeService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 系统角色数据范围service接口实现类
 *
 * @date 2020/11/5 下午4:32
 */
public class SysRoleDataScopeServiceImpl
		extends BaseServiceImpl<SysRoleDataScopeRequest, SysRoleDataScopeResponse, SysRoleDataScope>
		implements SysRoleDataScopeService, RoleDataScopeServiceApi {

	public SysRoleDataScopeServiceImpl() {
		super(SysRoleDataScopeRequest.class, SysRoleDataScopeResponse.class, SysRoleDataScope.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void grantDataScope(SysRoleRequest sysRoleParam) {
		Long roleId = sysRoleParam.getRoleId();

		// 删除所拥该角色已绑定的范围
		this.delByRoleId(roleId);

		// 批量新增-授权该角色数据范围
		if (ObjectUtil.isNotEmpty(sysRoleParam.getGrantOrgIdList())) {
			List<SysRoleDataScope> sysRoleDataScopeList = CollUtil.newArrayList();
			sysRoleParam.getGrantOrgIdList().forEach(orgId -> {
				SysRoleDataScope sysRoleDataScope = new SysRoleDataScope();
				sysRoleDataScope.setRoleId(roleId);
				sysRoleDataScope.setOrganizationId(orgId);
				sysRoleDataScopeList.add(sysRoleDataScope);
			});
			this.getRepository().insertMultiple(sysRoleDataScopeList);
		}
	}

	@Override
	public List<Long> getRoleDataScopeIdList(List<Long> roleIdList) {
		return this.getRepository()
				.select(getEntityClass(),
						c -> c.where(SysRoleDataScopeDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIdList)))
				.stream().map(SysRoleDataScope::getOrganizationId).toList();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
		SysRoleDataScope sysRoleDataScope = this.converterService.convert(sysRoleDataScopeRequest, getEntityClass());
		this.getRepository().insert(sysRoleDataScope);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
		SysRoleDataScope sysRoleDataScope = this.querySysRoleDataScopeById(sysRoleDataScopeRequest);
		this.getRepository().deleteByPrimaryKey(getEntityClass(), sysRoleDataScope.getRoleDataScopeId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delByRoleId(Long roleId) {
		SysRoleDataScope query = new SysRoleDataScope();
		query.setRoleId(roleId);
		this.getRepository().deleteBy(query);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delByOrgIds(Set<Long> orgIds) {
		this.getRepository().delete(getEntityClass(),
				c -> c.where(SysRoleDataScopeDynamicSqlSupport.organizationId, SqlBuilder.isIn(orgIds)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysRoleDataScopeResponse update(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
		SysRoleDataScope sysRoleDataScope = this.querySysRoleDataScopeById(sysRoleDataScopeRequest);
		this.converterService.copy(sysRoleDataScopeRequest, sysRoleDataScope);
		this.getRepository().updateByPrimaryKey(sysRoleDataScope);
		return this.converterService.convert(sysRoleDataScope, getResponseClass());
	}

	@Override
	public SysRoleDataScopeResponse detail(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
		return this.selectOne(sysRoleDataScopeRequest);
	}

	@Override
	public List<SysRoleDataScopeResponse> findList(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
		return this.select(sysRoleDataScopeRequest);
	}

	/**
	 * 根据主键查询
	 * @param sysRoleDataScopeRequest dto实体
	 * @date 2021/2/3 15:02
	 */
	private SysRoleDataScope querySysRoleDataScopeById(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
		return this.getRepository().get(getEntityClass(), sysRoleDataScopeRequest.getRoleDataScopeId());
	}

}
