/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.auth.api.pojo.auth;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

/**
 * 单点获取到的token
 *
 * @date 2021/5/25 22:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginWithTokenRequest extends BaseRequest {

	/**
	 * 从单点服务获取到的token
	 */
	@NotBlank(message = "token不能为空")
	private String token;

}
