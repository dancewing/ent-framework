/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.api.exception;

import io.entframework.kernel.mongodb.api.constants.MongodbConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * Mongodb模块的异常
 *
 * @date 2021/13/17 23:59
 */
public class MongodbException extends ServiceException {

    public MongodbException(AbstractExceptionEnum exception) {
        super(MongodbConstants.MONGODB_MODULE_NAME, exception);
    }

}
