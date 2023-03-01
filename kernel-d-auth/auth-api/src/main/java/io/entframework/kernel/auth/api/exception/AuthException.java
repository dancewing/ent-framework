/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 认证类异常
 *
 * @date 2020/10/15 15:59
 */
public class AuthException extends ServiceException {

    public AuthException(AbstractExceptionEnum exception, Object... params) {
        super(AuthConstants.AUTH_MODULE_NAME, exception.getErrorCode(),
                CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public AuthException(AbstractExceptionEnum exception) {
        super(AuthConstants.AUTH_MODULE_NAME, exception);
    }

}
