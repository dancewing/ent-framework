/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.api.exception;

import io.entframework.kernel.pay.api.constants.PayConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 支付模块的异常
 *
 * @date 2021/04/20 20:43
 */
public class PayException extends ServiceException {

    public PayException(AbstractExceptionEnum exception) {
        super(PayConstants.PAY_MODULE_NAME, exception);
    }

}
