/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.constants;

/**
 * 安全模块常量
 *
 * @date 2021/2/19 8:45
 */
public interface SecurityConstants {

	/**
	 * 安全模块的名称
	 */
	String SECURITY_MODULE_NAME = "kernel-d-security";

	/**
	 * 异常枚举的步进值
	 */
	String SECURITY_EXCEPTION_STEP_CODE = "28";

	/**
	 * XSS默认拦截范围
	 */
	String DEFAULT_XSS_PATTERN = "/*";

	/**
	 * 默认验证码的开关：关闭
	 */
	Boolean DEFAULT_CAPTCHA_OPEN = false;

	String SECURITY_PREFIX = "kernel.security";

}
