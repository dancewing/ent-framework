/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.system.api.exception.enums.resource;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 接口分组异常相关枚举
 *
 * @date 2021/05/21 15:03
 */
@Getter
public enum ApiGroupExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 查询结果不存在
	 */
	APIGROUP_NOT_EXISTED(
			RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "01",
			"查询结果不存在"),

	/**
	 * 根节点不允许操作
	 */
	ROOT_PROHIBIT_OPERATION(
			RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "02",
			"根节点不允许操作"),

	/**
	 * 父节点不能选择自己
	 */
	PARENT_NODE_ITSELF(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "03",
			"父节点不能选择自己"),

	/**
	 * 父节点不能选择自己的子节点
	 */
	NODE_NOT_ALLOWED_SUB_NODE(
			RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "04",
			"父节点不能选择自己的子节点");

	/**
	 * 错误编码
	 */
	private final String errorCode;

	/**
	 * 提示用户信息
	 */
	private final String userTip;

	ApiGroupExceptionEnum(String errorCode, String userTip) {
		this.errorCode = errorCode;
		this.userTip = userTip;
	}

}
