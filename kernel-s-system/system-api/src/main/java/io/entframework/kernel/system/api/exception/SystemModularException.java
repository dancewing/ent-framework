/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.system.api.constants.SystemConstants;

/**
 * 系统管理模块的异常
 *
 * @date 2020/11/4 15:50
 */
public class SystemModularException extends ServiceException {

    public SystemModularException(AbstractExceptionEnum exception, Object... params) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public SystemModularException(AbstractExceptionEnum exception) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception);
    }

}
