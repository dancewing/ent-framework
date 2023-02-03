/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.starter.init;

import io.entframework.kernel.auth.api.context.LoginUserHolder;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.resource.api.pojo.SysResourceRequest;
import io.entframework.kernel.resource.api.pojo.SysResourceResponse;
import io.entframework.kernel.resource.modular.service.SysResourceService;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.modular.service.SysRoleResourceService;
import io.entframework.kernel.system.modular.service.SysRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化admin管理员的服务
 *
 * @date 2020/12/17 21:56
 */
@Service
@Slf4j
public class InitAdminService {

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysResourceService sysResourceService;

	@Resource
	private SysRoleResourceService sysRoleResourceService;

	/**
	 * 初始化超级管理员，超级管理员拥有最高权限
	 *
	 * @date 2020/12/17 21:57
	 */
	@Transactional(rollbackFor = Exception.class)
	public void initSuperAdmin() {

		log.info("初始化SupperAdmin的资源权限");

		// 找到超级管理员的角色id
		SysRoleRequest request = new SysRoleRequest();
		request.setRoleCode(SystemConstants.SUPER_ADMIN_ROLE_CODE);
		SysRoleResponse superAdminRole = sysRoleService.selectOne(request);

		// 删除这个角色绑定的所有资源
		sysRoleResourceService.deleteRoleResourceListByRoleId(superAdminRole.getRoleId());

		// 找到所有Resource，将所有资源赋给这个角色
		List<SysResourceResponse> resources = sysResourceService.select(new SysResourceRequest());

		List<SysRoleResourceRequest> sysRoleResources = new ArrayList<>();
		for (SysResourceResponse resource : resources) {
			SysRoleResourceRequest sysRoleResource = new SysRoleResourceRequest();
			sysRoleResource.setResourceCode(resource.getResourceCode());
			sysRoleResource.setRoleId(superAdminRole.getRoleId());
			sysRoleResources.add(sysRoleResource);
		}
		//后台触发的插入操作，防止RecordableAutoFillInterceptor抛出异常
		LoginUser loginUser = new LoginUser();
		loginUser.setUserId(-1L);
		loginUser.setAccount("system");
		LoginUserHolder.set(loginUser);
		sysRoleResourceService.insertMultiple(sysRoleResources);
		LoginUserHolder.remove();
	}

}
