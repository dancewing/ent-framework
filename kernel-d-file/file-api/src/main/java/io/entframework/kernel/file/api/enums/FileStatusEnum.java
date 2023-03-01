/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文件状态
 *
 * @date 2020/12/16 12:00
 */
@Getter
@EnumHandler
public enum FileStatusEnum implements SupperEnum<String> {

	/**
	 * 新文件
	 * <p>
	 * 如果code相同，每次版本号替换都会把当前文件设置成最新文件
	 */
	NEW("1", "NEW"),

	/**
	 * 旧文件
	 */
	OLD("0", "OLD");

	@JsonValue
	@EnumValue
	private final String value;

	private final String label;

	FileStatusEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static FileStatusEnum valueToEnum(String value) {
		return SupperEnum.fromValue(FileStatusEnum.class, value);
	}

}
