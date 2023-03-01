/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地文件存储配置
 *
 * @date 2020/6/7 22:30
 */
@Data
@ConfigurationProperties(prefix = "kernel.file.local")
public class LocalFileProperties {

	/**
	 * 本地文件存储位置（linux）
	 */
	private String localFileSavePathLinux = "/tmp/tempFilePath";

	/**
	 * 本地文件存储位置（windows）
	 */
	private String localFileSavePathWin = "D:\\tempFilePath";

}
