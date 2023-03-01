/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.user.OnlineUserDTO;
import io.entframework.kernel.system.api.pojo.user.OnlineUserRequest;
import io.entframework.kernel.system.modular.service.SysUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import jakarta.validation.Valid;

import java.util.List;

/**
 * 在线用户管理
 *
 * @date 2021/1/11 22:52
 */
@RestController
@ApiResource(name = "在线用户管理")
public class OnlineUserController {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SessionManagerApi sessionManagerApi;

	/**
	 * 当前在线用户列表
	 *
	 * @date 2021/1/11 22:53
	 */
	@GetResource(name = "当前在线用户列表", path = "/sys-user/online-user-list")
	public ResponseData<List<OnlineUserDTO>> onlineUserList(OnlineUserRequest onlineUserRequest) {
		return ResponseData.ok(sysUserService.onlineUserList(onlineUserRequest));
	}

	/**
	 * 踢掉在线用户
	 *
	 * @date 2021/1/11 22:53
	 */
	@PostResource(name = "踢掉在线用户", path = "/sys-user/remove-session")
	public ResponseData<Void> removeSession(@Valid @RequestBody OnlineUserRequest onlineUserRequest) {
		sessionManagerApi.removeSession(onlineUserRequest.getToken());
		return ResponseData.OK_VOID;
	}

}
