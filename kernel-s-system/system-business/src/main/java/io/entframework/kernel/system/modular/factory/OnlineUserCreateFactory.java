/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.factory;

import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.system.api.pojo.user.OnlineUserDTO;

/**
 * 当前在线用户的创建工厂
 *
 * @date 2021/1/11 22:31
 */
public class OnlineUserCreateFactory {

	/**
	 * 登录用户转化为在线用户
	 *
	 * @date 2021/1/11 22:31
	 */
	public static OnlineUserDTO createOnlineUser(LoginUser loginUser) {

		OnlineUserDTO onlineUserResponse = new OnlineUserDTO();

		onlineUserResponse.setUserId(loginUser.getUserId());
		onlineUserResponse.setAccount(loginUser.getAccount());
		onlineUserResponse.setSex(loginUser.getSimpleUserInfo().getSex().getLabel());
		onlineUserResponse.setRealName(loginUser.getSimpleUserInfo().getRealName());
		onlineUserResponse.setNickName(loginUser.getSimpleUserInfo().getNickName());

		if (loginUser.getSimpleRoleInfoList() != null && loginUser.getSimpleRoleInfoList().size() > 0) {
			onlineUserResponse.setRoleName(loginUser.getSimpleRoleInfoList().get(0).getRoleName());
		}

		onlineUserResponse.setLoginTime(loginUser.getLoginTime());
		onlineUserResponse.setToken(loginUser.getToken());

		return onlineUserResponse;
	}

}
