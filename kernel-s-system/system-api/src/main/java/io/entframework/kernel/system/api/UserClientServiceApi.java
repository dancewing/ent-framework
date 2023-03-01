/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api;

import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.api.pojo.user.UserLoginInfoDTO;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface UserClientServiceApi {

	/**
	 * 根据用户id 判断用户是否存在
	 * @param userId 用户id
	 * @return 用户信息
	 * @date 2021/1/4 22:55
	 */
	@GetMapping(path = "/client/sys-user/user-exist")
	Boolean userExist(@RequestParam("userId") Long userId);

	/**
	 * 查询全部用户ID(剔除被删除的)
	 * @param sysUserRequest 查询参数
	 * @return List<Long> 用户id 集合
	 * @date 2021/1/4 22:09
	 */
	@GetMapping(path = "/client/sys-user/query-all-user-id-list")
	List<Long> queryAllUserIdList(@RequestBody SysUserRequest sysUserRequest);

	/**
	 * 获取用户登录需要的详细信息（用在第一次获取登录用户）
	 * @param account 账号
	 * @return 用户登录需要的详细信息
	 * @date 2020/10/20 16:59
	 */
	@GetMapping(path = "/client/sys-user/get-user-login-info")
	UserLoginInfoDTO getUserLoginInfo(@RequestParam("account") String account);

	/**
	 * 更新用户的登录信息，一般为更新用户的登录时间和ip
	 * @param userId 用户id
	 * @param date 用户登录时间
	 * @param ip 用户登录的ip
	 * @date 2020/10/21 14:13
	 */
	@GetMapping(path = "/client/sys-user/update-user-login-info")
	void updateUserLoginInfo(@RequestParam("userId") Long userId, @RequestParam("date") LocalDateTime date,
			@RequestParam("ip") String ip);

	/**
	 * 根据用户ID获取用户信息
	 * @param userId 用户ID
	 * @date 2021/1/9 19:00
	 */
	@GetMapping(path = "/client/sys-user/get-user-info-by-user-id")
	SysUserResponse getUserInfoByUserId(@RequestParam("userId") Long userId);

}
