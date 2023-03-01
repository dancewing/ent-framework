/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner.api.enums;

import lombok.Getter;

/**
 * 字段元数据类型
 *
 * @date 2022/1/11 9:25
 */
@Getter
public enum FieldMetadataTypeEnum {

	/**
	 * 字段
	 */
	FIELD(1),

	/**
	 * 泛型
	 */
	GENERIC(2);

	FieldMetadataTypeEnum(Integer code) {
		this.code = code;
	}

	private final Integer code;

}
