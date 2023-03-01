/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api;

import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;

/**
 * 当前登陆用户相关的一系列方法
 *
 * @date 2020/10/17 10:27
 */
public interface LoginUserApi {

	/**
	 * 获取当前登陆用户的token
	 * <p>
	 * 如果获取不到，返回null
	 * @return 当前用户的token或null
	 * @date 2020/10/17 11:05
	 */
	String getToken();

	/**
	 * 获取当前登陆用户
	 * <p>
	 * 如果获取不到当前登陆用户会抛出 AuthException
	 * @return 当前登陆用户信息
	 * @throws AuthException 权限异常
	 * @date 2020/10/17 10:27
	 */
	LoginUser getLoginUser() throws AuthException;

	/**
	 * 获取当前登陆用户
	 * <p>
	 * 如果获取不到当前登陆用户返回null
	 * @return 当前登录用户信息
	 * @date 2020/10/17 11:00
	 */
	LoginUser getLoginUserNullable();

	/**
	 * 获取是否是超级管理员的标识
	 * @return true-是超级管理员，false-不是超级管理员
	 * @date 2020/11/4 15:45
	 */
	boolean getSuperAdminFlag();

	/**
	 * 判断当前用户是否登录
	 * @return 是否登录，true是，false否
	 * @date 2020/10/17 11:02
	 */
	boolean hasLogin();

	/**
	 * 是否包含某个按钮的权限
	 * @param buttonCode 按钮的编码
	 * @return true-有权限，false-没有权限
	 * @date 2021/1/9 11:32
	 */
	boolean haveButton(String buttonCode);

}
