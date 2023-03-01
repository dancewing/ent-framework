/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.system.api.UserServiceApi;
import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.api.pojo.user.UserLoginInfoDTO;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SysUserClientController {

	@Resource
	private UserServiceApi userServiceApi;

	@GetMapping(path = "/client/sys-user/user-exist")
	public Boolean userExist(Long userId) {
		return userServiceApi.userExist(userId);
	}

	@GetMapping(path = "/client/sys-user/query-all-user-id-list")
	public List<Long> queryAllUserIdList(SysUserRequest sysUserRequest) {
		return userServiceApi.queryAllUserIdList(sysUserRequest);
	}

	@GetMapping(path = "/client/sys-user/get-user-login-info")
	public UserLoginInfoDTO getUserLoginInfo(String account) {
		return userServiceApi.getUserLoginInfo(account);
	}

	@GetMapping(path = "/client/sys-user/update-user-login-info")
	public void updateUserLoginInfo(Long userId, LocalDateTime date, String ip) {
		userServiceApi.updateUserLoginInfo(userId, date, ip);
	}

	@GetMapping(path = "/client/sys-user/get-user-info-by-user-id")
	public SysUserResponse getUserInfoByUserId(Long userId) {
		return userServiceApi.getUserInfoByUserId(userId);
	}

}
