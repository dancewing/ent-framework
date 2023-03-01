/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.log.api.LogRecordApi;

/**
 * 日志操作api的获取
 *
 * @date 2020/10/27 16:19
 */
public class LogRecordContext {

	/**
	 * 获取日志操作api
	 *
	 * @date 2020/10/27 16:19
	 */
	public static LogRecordApi me() {
		return SpringUtil.getBean(LogRecordApi.class);
	}

}
