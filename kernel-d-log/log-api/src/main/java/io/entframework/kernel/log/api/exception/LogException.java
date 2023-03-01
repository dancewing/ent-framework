/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.log.api.constants.LogConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 日志异常枚举
 *
 * @date 2020/10/15 15:59
 */
public class LogException extends ServiceException {

	public LogException(AbstractExceptionEnum exception, Object... params) {
		super(LogConstants.LOG_MODULE_NAME, exception.getErrorCode(),
				CharSequenceUtil.format(exception.getUserTip(), params));
	}

	public LogException(AbstractExceptionEnum exception) {
		super(LogConstants.LOG_MODULE_NAME, exception);
	}

}
