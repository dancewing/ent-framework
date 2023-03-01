/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.modular.service.SysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 个人信息控制器
 *
 * @date 2021/3/17 22:05
 */
@RestController
@ApiResource(name = "个人信息")
public class PersonalInfoController {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 更新用户个人信息
	 *
	 * @date 2020/11/6 13:50
	 */
	@PostResource(name = "个人信息_更新个人信息", path = "/sys-user/update-info", requiredPermission = false)
	public ResponseData<Void> updateInfo(
			@RequestBody @Validated(SysUserRequest.updateInfo.class) SysUserRequest sysUserRequest) {
		sysUserService.updateInfo(sysUserRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 修改密码
	 *
	 * @date 2020/11/6 13:50
	 */
	@PostResource(name = "个人信息_修改密码", path = "/sys-user/update-password", requiredPermission = false)
	public ResponseData<Void> updatePwd(
			@RequestBody @Validated(SysUserRequest.updatePwd.class) SysUserRequest sysUserRequest) {
		sysUserService.updatePassword(sysUserRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 修改头像
	 *
	 * @date 2020/11/6 13:48
	 */
	@PostResource(name = "个人信息_修改头像", path = "/sys-user/update-avatar", requiredPermission = false)
	public ResponseData<Void> updateAvatar(
			@RequestBody @Validated(SysUserRequest.updateAvatar.class) SysUserRequest sysUserRequest) {
		sysUserService.updateAvatar(sysUserRequest);
		return ResponseData.OK_VOID;
	}

}
