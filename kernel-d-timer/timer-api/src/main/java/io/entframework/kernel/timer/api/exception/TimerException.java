/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.timer.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.timer.api.constants.TimerConstants;

/**
 * 定时器任务的异常
 *
 * @date 2020/10/15 15:59
 */
public class TimerException extends ServiceException {

    public TimerException(AbstractExceptionEnum exception) {
        super(TimerConstants.TIMER_MODULE_NAME, exception);
    }

    public TimerException(AbstractExceptionEnum exception, Object... params) {
        super(TimerConstants.TIMER_MODULE_NAME, exception.getErrorCode(), CharSequenceUtil.format(exception.getUserTip(), params));
    }

}
