/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.migration.api.exception;

import io.entframework.kernel.migration.api.constants.MigrationConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 * migration模块异常
 *
 * @date 2021/7/6 15:07
 */
public class MigrationException extends ServiceException {

	public MigrationException(AbstractExceptionEnum exception) {
		super(MigrationConstants.MIGRATION_MODULE_NAME, exception);
	}

	public MigrationException(String errorCode, String userTip) {
		super(MigrationConstants.MIGRATION_MODULE_NAME, errorCode, userTip);
	}

}
