/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.message.api.constants.MessageConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 消息异常枚举
 *
 * @date 2021/1/1 20:55
 */
public class MessageException extends ServiceException {

    public MessageException(AbstractExceptionEnum exception, Object... params) {
        super(MessageConstants.MESSAGE_MODULE_NAME, exception.getErrorCode(),
                CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public MessageException(AbstractExceptionEnum exception) {
        super(MessageConstants.MESSAGE_MODULE_NAME, exception);
    }

}
