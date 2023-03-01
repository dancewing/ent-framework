/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.auth.api.pojo.auth;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 登录的请求参数
 *
 * @date 2020/10/19 14:02
 */
@Data
public class LoginRequest {

	/**
	 * 账号
	 */
	@NotBlank(message = "账号不能为空")
	@ChineseDescription("账号")
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	@ChineseDescription("密码")
	private String password;

	/**
	 * 记住我，不传就是false
	 */
	@ChineseDescription("记住我，不传就是false")
	private Boolean rememberMe = false;

	/**
	 * 验证码图形对应的缓存key
	 */
	@ChineseDescription("验证码图形对应的缓存key")
	private String verKey;

	/**
	 * 用户输入的验证码的值
	 */
	@ChineseDescription("用户输入的验证码的值")
	private String verCode;

	/**
	 * 是否写入cookie会话信息
	 */
	@ChineseDescription("是否写入cookie会话信息")
	private Boolean createCookie = false;

	/**
	 * 租户编码
	 */
	@ChineseDescription("租户编码")
	private String tenantCode;

}
