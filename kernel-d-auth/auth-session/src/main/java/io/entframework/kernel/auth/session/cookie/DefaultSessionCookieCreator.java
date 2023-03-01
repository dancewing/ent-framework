/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.auth.session.cookie;

import io.entframework.kernel.auth.api.cookie.SessionCookieCreator;

import jakarta.servlet.http.Cookie;

/**
 * 默认的cookie创建
 * <p>
 * 这里预留了expandCookieProp的接口可以拓展cookie的属性
 *
 * @date 2020/12/27 13:29
 */
public class DefaultSessionCookieCreator extends SessionCookieCreator {

	@Override
	public void expandCookieProp(Cookie cookie) {
		cookie.setHttpOnly(true);
		cookie.setPath("/");
	}

}
