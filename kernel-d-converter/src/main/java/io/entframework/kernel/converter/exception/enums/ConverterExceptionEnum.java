/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.converter.exception.enums;

import io.entframework.kernel.converter.constants.ConverterConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

@Getter
public enum ConverterExceptionEnum implements AbstractExceptionEnum {

	WRONG_ARGS_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConverterConstants.CONVERTER_EXCEPTION_STEP_CODE + "01",
			"参数为空"),
	NONE_CONVERTER_FOUND(
			RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConverterConstants.CONVERTER_EXCEPTION_STEP_CODE + "02",
			"未找到对应的转换器"),
	CONVERTER_NO_COPY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConverterConstants.CONVERTER_EXCEPTION_STEP_CODE + "03",
			"转化器类型错误"),
	CONVERTER_ERROR_RETURN_VALUE(
			RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConverterConstants.CONVERTER_EXCEPTION_STEP_CODE + "04",
			"转化器返回值错误"),

	;

	/**
	 * 错误编码
	 */
	private final String errorCode;

	/**
	 * 提示用户信息
	 */
	private final String userTip;

	ConverterExceptionEnum(String errorCode, String userTip) {
		this.errorCode = errorCode;
		this.userTip = userTip;
	}

}
