/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.socket.api.exception;

import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.socket.api.constants.SocketConstants;

/**
 * Socket模块异常
 *
 * @date 2021/6/1 上午11:23
 */
public class SocketException extends ServiceException {

    public SocketException(AbstractExceptionEnum exception) {
        super(SocketConstants.SOCKET_MODULE_NAME, exception);
    }

    public SocketException(String errorCode, String userTip) {
        super(SocketConstants.SOCKET_MODULE_NAME, errorCode, userTip);
    }

}
