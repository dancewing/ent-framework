/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.login;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 校验token是否正确的请求参数
 *
 * @date 2021/6/18 15:29
 */
@Data
public class ValidateTokenRequest {

	/**
	 * 用户的登录token
	 */
	@NotBlank(message = "token不能为空")
	private String token;

}
