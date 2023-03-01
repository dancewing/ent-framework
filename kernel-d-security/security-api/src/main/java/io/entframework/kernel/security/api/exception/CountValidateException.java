/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.security.api.constants.SecurityConstants;

/**
 * 计数器校验异常
 *
 * @date 2020/11/14 17:53
 */
public class CountValidateException extends ServiceException {

	public CountValidateException(AbstractExceptionEnum exception, Object... params) {
		super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(),
				CharSequenceUtil.format(exception.getUserTip(), params));
	}

	public CountValidateException(AbstractExceptionEnum exception) {
		super(SecurityConstants.SECURITY_MODULE_NAME, exception);
	}

}
