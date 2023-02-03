/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.dict.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.dict.api.constants.DictConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 字典模块的异常
 *
 * @date 2020/10/29 11:57
 */
public class DictException extends ServiceException {

    public DictException(AbstractExceptionEnum exception, Object... params) {
        super(DictConstants.DICT_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public DictException(AbstractExceptionEnum exception) {
        super(DictConstants.DICT_MODULE_NAME, exception);
    }

}
