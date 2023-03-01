/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.converter.exception;

import io.entframework.kernel.converter.constants.ConverterConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

public class ConverterException extends ServiceException {

	public ConverterException(String errorCode, String userTip) {
		super(ConverterConstants.CONVERTER_MODULE_NAME, errorCode, userTip);
	}

	public ConverterException(AbstractExceptionEnum exception) {
		super(ConverterConstants.CONVERTER_MODULE_NAME, exception);
	}

	public ConverterException(String errorCode, String userTip, Object data) {
		super(ConverterConstants.CONVERTER_MODULE_NAME, errorCode, userTip, data);
	}

}
