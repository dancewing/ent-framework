/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.pojo.resource;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取资源通过url请求
 *
 * @date 2020/10/19 22:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceUrlParam extends BaseRequest {

	private String url;

}
