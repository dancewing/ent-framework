/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.cache.api.exception;

import io.entframework.kernel.cache.api.constants.CacheConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

@Getter
public enum CacheExceptionEnum implements AbstractExceptionEnum {

	/**
	 *
	 */
	REDIS_CACHE_ALIAS_MISSING(
			RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + CacheConstants.CACHE_EXCEPTION_STEP_CODE + "01",
			"Redis 配置节点缺少别名：{}"),
	/**
	 *
	 */
	REDIS_CACHE_ALIAS_DUPLICATED(
			RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + CacheConstants.CACHE_EXCEPTION_STEP_CODE + "02",
			"Redis 配置节点别名重复：{}"),
	/**
	 *
	 */
	REDIS_CACHE_TYPE_UNKNOWN(
			RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + CacheConstants.CACHE_EXCEPTION_STEP_CODE + "03",
			"Cache 类型不支持：{}"),

	/**
	 *
	 */
	REDIS_CACHE_NOT_SET(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + CacheConstants.CACHE_EXCEPTION_STEP_CODE + "04",
			"Redis 配置节点未配置");

	/**
	 * 错误编码
	 */
	private final String errorCode;

	/**
	 * 提示用户信息
	 */
	private final String userTip;

	CacheExceptionEnum(String errorCode, String userTip) {
		this.errorCode = errorCode;
		this.userTip = userTip;
	}

}
