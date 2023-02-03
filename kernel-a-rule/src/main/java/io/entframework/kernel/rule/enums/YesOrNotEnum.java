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
 * 是或否的枚举，一般用在数据库字段，例如del_flag字段，char(1)，填写Y或N
 *
 * @date 2020/4/13 22:59
 */
@Getter
@EnumHandler
public enum YesOrNotEnum implements SupperEnum<String> {

	/**
	 * 是
	 */
	Y("Y", "是"),

	/**
	 * 否
	 */
	N("N", "否");

	@JsonValue
	@EnumValue
	private final String value;

	private final String label;

	YesOrNotEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static YesOrNotEnum valueToEnum(String value) {
		return SupperEnum.fromValue(YesOrNotEnum.class, value);
	}

}
