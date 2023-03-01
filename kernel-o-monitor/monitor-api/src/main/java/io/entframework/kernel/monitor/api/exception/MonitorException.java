/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api.exception;

import io.entframework.kernel.monitor.api.constants.MonitorConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 监控模块异常
 *
 * @date 2021/1/31 22:35
 */
public class MonitorException extends ServiceException {

    public MonitorException(AbstractExceptionEnum exception) {
        super(MonitorConstants.MONITOR_MODULE_NAME, exception);
    }

}
