/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.sms.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.sms.api.constants.SmsConstants;

/**
 * 短信发送的异常
 *
 * @date 2020/10/26 16:53
 */
public class SmsException extends ServiceException {

    public SmsException(AbstractExceptionEnum exception) {
        super(SmsConstants.SMS_MODULE_NAME, exception);
    }

    public SmsException(AbstractExceptionEnum exception, Object... params) {
        super(SmsConstants.SMS_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

}
