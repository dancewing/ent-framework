/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.api.context;

import io.entframework.kernel.auth.api.pojo.login.LoginUser;

/**
 * 当前登录用户的临时保存容器
 *
 * @date 2021/3/23 17:38
 */
public class LoginUserHolder {

	private static final ThreadLocal<LoginUser> LONGIN_USER_HOLDER = new ThreadLocal<>();

	/**
	 * set holder中内容
	 *
	 * @date 2021/3/23 17:41
	 */
	public static void set(LoginUser abstractLoginUser) {
		LONGIN_USER_HOLDER.set(abstractLoginUser);
	}

	/**
	 * 获取holder中的值
	 *
	 * @date 2021/3/23 17:41
	 */
	public static LoginUser get() {
		return LONGIN_USER_HOLDER.get();
	}

	/**
	 * 删除保存的用户
	 *
	 * @date 2021/3/23 17:42
	 */
	public static void remove() {
		LONGIN_USER_HOLDER.remove();
	}

}
