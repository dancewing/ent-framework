/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.config.api.exception;

import io.entframework.kernel.config.api.constants.ConfigConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @date 2020/10/15 15:59
 */
public class ConfigException extends ServiceException {

	public ConfigException(String errorCode, String userTip) {
		super(ConfigConstants.CONFIG_MODULE_NAME, errorCode, userTip);
	}

	public ConfigException(AbstractExceptionEnum exception) {
		super(ConfigConstants.CONFIG_MODULE_NAME, exception);
	}

	public ConfigException(AbstractExceptionEnum exception, String userTip) {
		super(ConfigConstants.CONFIG_MODULE_NAME, exception.getErrorCode(), userTip);
	}

}
