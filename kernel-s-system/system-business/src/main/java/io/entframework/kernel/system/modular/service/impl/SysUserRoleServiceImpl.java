/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.collection.CollUtil;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.mds.repository.BaseRepository;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserRoleResponse;
import io.entframework.kernel.system.modular.entity.SysUserRole;
import io.entframework.kernel.system.modular.mapper.SysUserRoleDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysUserRoleService;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户角色service接口实现类
 *
 * @date 2020/11/6 10:28
 */
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleRequest, SysUserRoleResponse, SysUserRole> implements SysUserRoleService {

	@Resource(name = "userRoleCacheApi")
	private CacheOperatorApi<List<Long>> userRoleCacheApi;

	public SysUserRoleServiceImpl(BaseRepository<SysUserRole> baseRepository) {
		super(baseRepository, SysUserRoleRequest.class, SysUserRoleResponse.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysUserRoleRequest sysUserRoleRequest) {
		SysUserRole sysUserRole = this.converterService.convert(sysUserRoleRequest, getEntityClass());
		this.getRepository().insert(sysUserRole);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysUserRoleRequest sysUserRoleRequest) {
		SysUserRole sysUserRole = querySysUserRoleById(sysUserRoleRequest);
		this.getRepository().deleteByPrimaryKey(sysUserRole.getUserRoleId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delByUserId(Long userId) {
		this.getRepository().delete(c -> c.where(SysUserRoleDynamicSqlSupport.userId, SqlBuilder.isEqualTo(userId)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysUserRoleResponse update(SysUserRoleRequest sysUserRoleRequest) {
		SysUserRole sysUserRole = this.querySysUserRoleById(sysUserRoleRequest);
		this.converterService.copy(sysUserRoleRequest, sysUserRole);
		this.getRepository().updateByPrimaryKey(sysUserRole);
		return this.converterService.convert(sysUserRole, getResponseClass());
	}

	@Override
	public SysUserRole detail(SysUserRoleRequest sysUserRoleRequest) {
		return this.getRepository().get(sysUserRoleRequest.getUserRoleId());
	}

	@Override
	public List<SysUserRole> findList(SysUserRoleRequest sysUserRoleRequest) {
		SysUserRole query = this.converterService.convert(sysUserRoleRequest, getEntityClass());
		return this.getRepository().selectBy(query);
	}

	@Override
	public List<SysUserRole> findListByUserId(Long userId) {
		SysUserRoleRequest sysUserRoleRequest = new SysUserRoleRequest();
		sysUserRoleRequest.setUserId(userId);
		return this.findList(sysUserRoleRequest);
	}

	@Override

	public List<Long> findRoleIdsByUserId(Long userId) {

		// 先从缓存获取用户绑定的角色
		String key = String.valueOf(userId);
		List<Long> userRolesCache = userRoleCacheApi.get(key);
		if (userRolesCache != null) {
			return userRolesCache;
		}

		// 从数据库查询角色
		SysUserRoleRequest sysUserRoleRequest = new SysUserRoleRequest();
		sysUserRoleRequest.setUserId(userId);
		List<SysUserRole> sysUserRoleList = this.findList(sysUserRoleRequest);
		List<Long> userRoles = sysUserRoleList.stream().map(SysUserRole::getRoleId).toList();
		userRoleCacheApi.put(key, userRoles);
		return userRoles;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void assignRoles(SysUserRequest sysUserRequest) {
		// 获取用户id
		Long userId = sysUserRequest.getUserId();

		// 删除已有角色
		this.delByUserId(userId);

		// 为该用户授权角色
		List<Long> rileIds = sysUserRequest.getGrantRoleIdList();

		// 批量添加角色
		List<SysUserRole> sysUserRoleList = CollUtil.newArrayList();
		rileIds.forEach(roleId -> {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			sysUserRole.setRoleId(roleId);
			sysUserRoleList.add(sysUserRole);
		});

		this.getRepository().insertMultiple(sysUserRoleList);

		// 清除用户角色的缓存
		userRoleCacheApi.remove(String.valueOf(userId));
	}

	/**
	 * 根据主键查询
	 *
	 * @param sysUserRoleRequest dto实体
	 * @return
	 * @date 2021/2/3 15:02
	 */
	private SysUserRole querySysUserRoleById(SysUserRoleRequest sysUserRoleRequest) {
		return this.getRepository().get(sysUserRoleRequest.getUserRoleId());
	}

}
