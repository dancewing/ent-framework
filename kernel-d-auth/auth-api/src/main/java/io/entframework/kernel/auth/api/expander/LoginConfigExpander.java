/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.auth.api.expander;

import io.entframework.kernel.config.api.context.ConfigContext;

/**
 * 登录相关配置快速获取
 */
public class LoginConfigExpander {

	/**
	 * 获取帐号错误次数校验开关
	 *
	 * @date 2022/1/24 15:48
	 */
	public static boolean getAccountErrorDetectionFlag() {
		return ConfigContext.me().getSysConfigValueWithDefault("ACCOUNT_ERROR_DETECTION", Boolean.class, false);
	}

}
