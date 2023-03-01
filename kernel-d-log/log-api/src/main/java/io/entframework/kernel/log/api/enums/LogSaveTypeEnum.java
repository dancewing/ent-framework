/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api.enums;

import lombok.Getter;

/**
 * 日志存储的方式，数据库还是文件
 *
 * @date 2020/12/24 14:08
 */
@Getter
public enum LogSaveTypeEnum {

	/**
	 * 存储到数据库
	 */
	DB("db"),

	/**
	 * 存储到文件
	 */
	FILE("file");

	LogSaveTypeEnum(String code) {
		this.code = code;
	}

	private final String code;

}
