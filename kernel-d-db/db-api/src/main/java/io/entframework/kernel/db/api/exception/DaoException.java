/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.db.api.constants.DbConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 数据库操作异常
 *
 * @date 2020/10/15 15:59
 */
public class DaoException extends ServiceException {

    public DaoException(AbstractExceptionEnum exception) {
        super(DbConstants.DB_MODULE_NAME, exception);
    }

    public DaoException(AbstractExceptionEnum exception, Object... params) {
        super(DbConstants.DB_MODULE_NAME, exception.getErrorCode(),
                CharSequenceUtil.format(exception.getUserTip(), params));
    }

}
