/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api.config;

import io.entframework.kernel.log.api.constants.LogConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置信息
 *
 * @date 2020-12-20 13:53
 **/
@Data
@ConfigurationProperties(prefix = LogConstants.SYS_LOG_PREFIX)
public class SysLogProperties {

	/**
	 * 日志存储类型：db-数据库，file-文件，默认存储在数据库中
	 */
	private String type = "db";

	/**
	 * file存储类型日志文件的存储位置
	 */
	private String fileSavePath = "_sys_log";

	/**
	 * 全局日志记录，如果开启则所有请求都将记录日志
	 */
	private boolean requestLogEnabled = true;

}
