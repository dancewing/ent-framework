/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.jwt.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.jwt.api.constants.JwtConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * jwt异常
 *
 * @date 2020/10/15 15:59
 */
public class JwtException extends ServiceException {

    public JwtException(AbstractExceptionEnum exception, Object... params) {
        super(JwtConstants.JWT_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public JwtException(AbstractExceptionEnum exception) {
        super(JwtConstants.JWT_MODULE_NAME, exception);
    }

}
