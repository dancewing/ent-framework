/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.i18n.api.constants.TranslationConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 多语言翻译的异常
 *
 * @date 2020/10/15 15:59
 */
public class TranslationException extends ServiceException {

    public TranslationException(AbstractExceptionEnum exception, Object... params) {
        super(TranslationConstants.I18N_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

    public TranslationException(AbstractExceptionEnum exception) {
        super(TranslationConstants.I18N_MODULE_NAME, exception);
    }

}
