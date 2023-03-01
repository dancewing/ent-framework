/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.api.pojo.aliyun;

import lombok.Data;

/**
 * 阿里云邮件发送的配置
 *
 * @date 2020/10/30 22:35
 */
@Data
public class AliyunMailSenderProperties {

	/**
	 * 发送邮件的key
	 */
	private String accessKeyId;

	/**
	 * 发送邮件的secret
	 */
	private String accessKeySecret;

	/**
	 * 发信人的地址，控制台配置
	 */
	private String accountName;

}
