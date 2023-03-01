/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.pojo.resource;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取用户资源请求
 *
 * @date 2020/10/19 22:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResourceParam extends BaseRequest {

	/**
	 * 用户id
	 */
	private String userId;

}
