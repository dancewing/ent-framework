/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pinyin.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.pinyin.api.constants.PinyinConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import lombok.Getter;

/**
 * 拼音异常
 *
 * @date 2020/12/3 18:10
 */
@Getter
public class PinyinException extends ServiceException {

	public PinyinException(AbstractExceptionEnum exceptionEnum) {
		super(PinyinConstants.PINYIN_MODULE_NAME, exceptionEnum);
	}

	public PinyinException(AbstractExceptionEnum exception, Object... params) {
		super(PinyinConstants.PINYIN_MODULE_NAME, exception.getErrorCode(),
				CharSequenceUtil.format(exception.getUserTip(), params));
	}

}
