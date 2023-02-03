/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.request.encrypt.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.security.request.encrypt.constants.EncryptionConstants;

/**
 * 请求解密，响应加密 异常
 *
 * @date 2021/3/23 12:54
 */
public class EncryptionException extends ServiceException {

    public EncryptionException(AbstractExceptionEnum exception, Object... params) {
        super(EncryptionConstants.ENCRYPTION_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public EncryptionException(AbstractExceptionEnum exception) {
        super(EncryptionConstants.ENCRYPTION_MODULE_NAME, exception);
    }

}
