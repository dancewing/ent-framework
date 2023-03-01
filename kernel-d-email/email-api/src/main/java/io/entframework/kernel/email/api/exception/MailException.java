/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.api.exception;

import io.entframework.kernel.email.api.constants.MailConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import lombok.Getter;

/**
 * 邮件发送异常
 *
 * @date 2018-07-06-下午3:00
 */
@Getter
public class MailException extends ServiceException {

	public MailException(String errorCode, String userTip) {
		super(MailConstants.MAIL_MODULE_NAME, errorCode, userTip);
	}

	public MailException(AbstractExceptionEnum exceptionEnum, String userTip) {
		super(MailConstants.MAIL_MODULE_NAME, exceptionEnum.getErrorCode(), userTip);
	}

	public MailException(AbstractExceptionEnum exceptionEnum) {
		super(MailConstants.MAIL_MODULE_NAME, exceptionEnum);
	}

}
