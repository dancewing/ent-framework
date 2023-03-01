/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息优先级
 *
 * @date 2021/1/8 13:26
 */
@Getter
@EnumHandler
public enum MessagePriorityLevelEnum {

	/**
	 * 高
	 */
	HIGH("high", "高"),

	/**
	 * 中
	 */
	MIDDLE("middle", "中"),

	/**
	 * 低
	 */
	LOW("low", "低");

	@JsonValue
	@EnumValue
	private final String code;

	private final String name;

	MessagePriorityLevelEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(String code) {
		if (code == null) {
			return null;
		}
		for (MessagePriorityLevelEnum flagEnum : MessagePriorityLevelEnum.values()) {
			if (flagEnum.getCode().equals(code)) {
				return flagEnum.name;
			}
		}
		return null;
	}

}
