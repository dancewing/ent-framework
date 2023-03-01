/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.api.exception.enums;

import io.entframework.kernel.i18n.api.constants.TranslationConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 多语言翻译的异常枚举
 *
 * @date 2021/1/24 16:40
 */
@Getter
public enum TranslationExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 多语言记录不存在
	 */
	NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TranslationConstants.I18N_EXCEPTION_STEP_CODE + "01",
			"多语言记录不存在，id为：{}");

	/**
	 * 错误编码
	 */
	private final String errorCode;

	/**
	 * 提示用户信息
	 */
	private final String userTip;

	TranslationExceptionEnum(String errorCode, String userTip) {
		this.errorCode = errorCode;
		this.userTip = userTip;
	}

}
