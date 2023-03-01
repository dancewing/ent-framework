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
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.user.SysUserDataScopeExceptionEnum;
import io.entframework.kernel.system.api.pojo.request.SysUserDataScopeRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserDataScopeResponse;
import io.entframework.kernel.system.modular.entity.SysUserDataScope;
import io.entframework.kernel.system.modular.entity.SysUserDataScopeDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysUserDataScopeService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 系统用户数据范围service接口实现类
 *
 * @date 2020/11/6 10:28
 */
public class SysUserDataScopeServiceImpl
		extends BaseServiceImpl<SysUserDataScopeRequest, SysUserDataScopeResponse, SysUserDataScope>
		implements SysUserDataScopeService {

	public SysUserDataScopeServiceImpl() {
		super(SysUserDataScopeRequest.class, SysUserDataScopeResponse.class, SysUserDataScope.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void assignData(SysUserRequest sysUserRequest) {

		// 获取用户id
		Long userId = sysUserRequest.getUserId();

		// 删除用户所有授权范围
		this.delByUserId(userId);

		List<Long> orgIds = sysUserRequest.getGrantOrgIdList();

		// 授权组织机构数据范围给用户
		List<SysUserDataScope> sysUserDataScopeList = CollUtil.newArrayList();

		// 批量添加数据范围
		orgIds.forEach(orgId -> {
			SysUserDataScope sysUserDataScope = new SysUserDataScope();
			sysUserDataScope.setUserId(userId);
			sysUserDataScope.setOrgId(orgId);
			sysUserDataScopeList.add(sysUserDataScope);

		});
		sysUserDataScopeList.forEach(this.getRepository()::insert);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysUserDataScopeRequest sysUserDataScopeRequest) {
		SysUserDataScope sysUserDataScope = this.converterService.convert(sysUserDataScopeRequest, getEntityClass());
		this.getRepository().insert(sysUserDataScope);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysUserDataScopeRequest sysUserDataScopeRequest) {
		this.getRepository().deleteByPrimaryKey(getEntityClass(), sysUserDataScopeRequest.getUserDataScopeId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delByUserId(Long userId) {
		if (ObjectUtil.isNotEmpty(userId)) {
			DeleteDSLCompleter dslCompleter = c -> c.where(SysUserDataScopeDynamicSqlSupport.userId,
					SqlBuilder.isEqualTo(userId));
			this.getRepository().delete(dslCompleter);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysUserDataScopeResponse update(SysUserDataScopeRequest sysUserDataScopeRequest) {
		SysUserDataScope sysUserDataScope = this.querySysUserRoleById(sysUserDataScopeRequest);
		this.converterService.copy(sysUserDataScopeRequest, sysUserDataScope);
		this.getRepository().updateByPrimaryKey(sysUserDataScope);
		return this.converterService.convert(sysUserDataScope, getResponseClass());
	}

	@Override
	public SysUserDataScope detail(SysUserDataScopeRequest sysUserDataScopeRequest) {
		return this.getRepository().get(getEntityClass(), sysUserDataScopeRequest.getUserDataScopeId());
	}

	@Override
	public List<Long> findOrgIdsByUserId(Long uerId) {
		SysUserDataScopeRequest sysUserDataScopeRequest = new SysUserDataScopeRequest();
		sysUserDataScopeRequest.setUserId(uerId);
		List<SysUserDataScope> sysUserDataScopeList = this.findList(sysUserDataScopeRequest);
		return sysUserDataScopeList.stream().map(SysUserDataScope::getOrgId).toList();
	}

	@Override
	public List<SysUserDataScope> findList(SysUserDataScopeRequest sysUserDataScopeRequest) {
		SysUserDataScope query = this.converterService.convert(sysUserDataScopeRequest, getEntityClass());
		return this.getRepository().selectBy(query);
	}

	public int delete(Collection<Long> organizationIds) {
		DeleteStatementProvider deleteStatementProvider = SqlBuilder
				.deleteFrom(SysUserDataScopeDynamicSqlSupport.sysUserDataScope)
				.where(SysUserDataScopeDynamicSqlSupport.orgId, SqlBuilder.isIn(organizationIds)).build()
				.render(RenderingStrategies.MYBATIS3);
		return this.getRepository().delete(deleteStatementProvider);
	}

	/**
	 * 根据主键查询
	 * @param sysUserDataScopeRequest dto实体
	 * @date 2021/2/3 15:02
	 */
	private SysUserDataScope querySysUserRoleById(SysUserDataScopeRequest sysUserDataScopeRequest) {
		Optional<SysUserDataScope> sysUserDataScope = this.getRepository().selectByPrimaryKey(getEntityClass(),
				sysUserDataScopeRequest.getUserDataScopeId());
		if (sysUserDataScope.isEmpty()) {
			throw new SystemModularException(SysUserDataScopeExceptionEnum.USER_DATA_SCOPE_NOT_EXIST,
					sysUserDataScopeRequest.getUserDataScopeId());
		}
		return sysUserDataScope.get();
	}

}
