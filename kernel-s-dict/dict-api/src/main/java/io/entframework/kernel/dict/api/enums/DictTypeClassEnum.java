/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.dict.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 字典类型的分类枚举
 *
 * @date 2020/10/30 10:19
 */
@Getter
@EnumHandler
public enum DictTypeClassEnum {

	/**
	 * 业务类型
	 */
	BUSINESS_TYPE(1),

	/**
	 * 系统类型
	 */
	SYSTEM_TYPE(2);

	@EnumValue
	@JsonValue
	private final Integer code;

	DictTypeClassEnum(Integer code) {
		this.code = code;
	}

}
