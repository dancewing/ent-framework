/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.sms.modular.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.sms.modular.enums.SmsSendSourceEnum;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 验证短信的参数
 *
 * @date 2020/10/26 21:32
 */
@Data
public class SysSmsVerifyParam {

	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	@ChineseDescription("手机号")
	private String phone;

	/**
	 * 验证码
	 */
	@NotBlank(message = "验证码不能为空")
	@ChineseDescription("验证码")
	private String code;

	/**
	 * 模板号
	 */
	@NotBlank(message = "模板号不能为空")
	@ChineseDescription("模板号")
	private String templateCode;

	/**
	 * 来源
	 */
	@ChineseDescription("来源")
	private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

}
