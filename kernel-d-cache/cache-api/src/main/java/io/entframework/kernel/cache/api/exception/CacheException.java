/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.cache.api.exception;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.cache.api.constants.CacheConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;

/**
 *
 */
public class CacheException extends ServiceException {

	public CacheException(String errorCode, String userTip) {
		super(CacheConstants.CACHE_MODULE_NAME, errorCode, userTip);
	}

	public CacheException(AbstractExceptionEnum exception, Object... params) {
		super(CacheConstants.CACHE_MODULE_NAME, exception.getErrorCode(),
				CharSequenceUtil.format(exception.getUserTip(), params));
	}

	public CacheException(AbstractExceptionEnum exception) {
		super(CacheConstants.CACHE_MODULE_NAME, exception);
	}

}
