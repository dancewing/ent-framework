/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * 性别的枚举
 *
 * @date 2020/10/17 10:01
 */
@Getter
@EnumHandler
public enum GenderEnum implements SupperEnum<String> {

	/**
	 * 男
	 */
	M("M", "男"),

	/**
	 * 女
	 */
	F("F", "女");

	@JsonValue
	@EnumValue
	private final String value;

	private final String label;

	GenderEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static GenderEnum valueToEnum(String value) {
		return SupperEnum.fromValue(GenderEnum.class, value);
	}

}
