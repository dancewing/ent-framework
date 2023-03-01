/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.user;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 当前的在线用户的信息请求
 *
 * @date 2021/1/11 22:30
 */
@Data
public class OnlineUserRequest {

	/**
	 * 用户的token
	 */
	@NotBlank(message = "参数token不能为空")
	@ChineseDescription("用户的token")
	private String token;

	/**
	 * 用户账号
	 */
	@ChineseDescription("用户账号")
	private String account;

}
