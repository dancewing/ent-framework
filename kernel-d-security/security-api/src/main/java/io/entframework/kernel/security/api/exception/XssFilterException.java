/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.security.api.constants.SecurityConstants;

/**
 * XSS过滤异常
 *
 * @date 2021/1/13 23:22
 */
public class XssFilterException extends ServiceException {

    public XssFilterException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(),
                CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public XssFilterException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
