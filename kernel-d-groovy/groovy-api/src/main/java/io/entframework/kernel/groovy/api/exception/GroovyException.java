/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.groovy.api.exception;

import io.entframework.kernel.groovy.api.constants.GroovyConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * groovy脚本执行异常
 *
 * @date 2021/1/22 16:36
 */
public class GroovyException extends ServiceException {

    public GroovyException(AbstractExceptionEnum exception, String userTip) {
        super(GroovyConstants.GROOVY_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public GroovyException(AbstractExceptionEnum exception) {
        super(GroovyConstants.GROOVY_MODULE_NAME, exception);
    }

}
