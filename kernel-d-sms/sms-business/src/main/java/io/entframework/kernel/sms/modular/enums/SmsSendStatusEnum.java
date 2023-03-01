/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.sms.modular.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 短信发送状态枚举
 *
 * @date 2020/10/26 21:29
 */
@Getter
@EnumHandler
public enum SmsSendStatusEnum implements SupperEnum<Integer> {

	/**
	 * 未发送
	 */
	WAITING(0, "未发送"),

	/**
	 * 发送成功
	 */
	SUCCESS(1, "发送成功"),

	/**
	 * 发送失败
	 */
	FAILED(2, "发送失败"),

	/**
	 * 失效
	 */
	INVALID(3, "失效");

	@JsonValue
	@EnumValue
	private final Integer value;

	private final String label;

	SmsSendStatusEnum(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static SmsSendStatusEnum valueToEnum(Integer value) {
		return SupperEnum.fromValue(SmsSendStatusEnum.class, value);
	}

}
