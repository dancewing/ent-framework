/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.security.api.constants.SecurityConstants;

/**
 * 安全模块异常
 *
 * @date 2021/2/19 8:48
 */
public class SecurityException extends ServiceException {

    public SecurityException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public SecurityException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
