/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;

/**
 * 资源模块的异常
 *
 * @date 2020/11/3 13:54
 */
public class ScannerException extends ServiceException {

	public ScannerException(AbstractExceptionEnum exception) {
		super(ScannerConstants.RESOURCE_MODULE_NAME, exception);
	}

	public ScannerException(AbstractExceptionEnum exception, Object... params) {
		super(ScannerConstants.RESOURCE_MODULE_NAME, exception.getErrorCode(),
				CharSequenceUtil.format(exception.getUserTip(), params));
	}

}
