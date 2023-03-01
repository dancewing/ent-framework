/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 字段类型枚举
 *
 * @date 2022/1/1 22:29
 */
@Getter
@EnumHandler
public enum ThemeFieldTypeEnum implements SupperEnum<String> {

	/**
	 * 字符串类型
	 */
	STRING("string", "字符串类型"),

	/**
	 * 文件类型
	 */
	FILE("file", "文件类型");

	@JsonValue
	@EnumValue
	private final String value;

	private final String label;

	ThemeFieldTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static ThemeFieldTypeEnum valueToEnum(String value) {
		return SupperEnum.fromValue(ThemeFieldTypeEnum.class, value);
	}

}
