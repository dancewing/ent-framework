/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.office.api.exception;

import io.entframework.kernel.office.api.constants.OfficeConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * Office模块异常
 *
 * @date 2020/11/4 10:15
 */
public class OfficeException extends ServiceException {

    public OfficeException(AbstractExceptionEnum exception) {
        super(OfficeConstants.OFFICE_MODULE_NAME, exception);
    }

    public OfficeException(String errorCode, String userTip) {
        super(OfficeConstants.OFFICE_MODULE_NAME, errorCode, userTip);
    }

}
