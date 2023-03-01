/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.api.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SSO的配置
 *
 * @date 2021/5/25 22:28
 */
@Data
@ConfigurationProperties(prefix = "kernel.auth.sso")
public class SsoProperties {

	/**
	 * 是否开启，true-开启单点，false-关闭单点
	 */
	private boolean openFlag = false;

}
