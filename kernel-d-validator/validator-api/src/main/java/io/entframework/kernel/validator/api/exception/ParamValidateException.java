/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.validator.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.validator.api.constants.ValidatorConstants;

/**
 * 参数校验异常
 *
 * @date 2020/10/15 15:59
 */
public class ParamValidateException extends ServiceException {

    public ParamValidateException(AbstractExceptionEnum exception, Object... params) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public ParamValidateException(AbstractExceptionEnum exception) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception);
    }

}
