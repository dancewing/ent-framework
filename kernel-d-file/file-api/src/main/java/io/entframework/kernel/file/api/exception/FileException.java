/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @date 2020/10/15 15:59
 */
public class FileException extends ServiceException {

    public FileException(AbstractExceptionEnum exception) {
        super(FileConstants.FILE_MODULE_NAME, exception);
    }

    public FileException(AbstractExceptionEnum exception, Object... params) {
        super(FileConstants.FILE_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

}
