/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.security.clear;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.rule.threadlocal.RemoveThreadLocalApi;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.Map;

/**
 * 清空程序中的ThreadLocal
 *
 * @date 2021/10/29 11:11
 */
@Slf4j
public class ClearThreadLocalFilter implements Filter {

	public static final String NAME = "ClearThreadLocalFilter";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		}
		finally {

			try {
				Map<String, RemoveThreadLocalApi> beansOfType = SpringUtil.getBeansOfType(RemoveThreadLocalApi.class);
				if (beansOfType != null) {
					for (Map.Entry<String, RemoveThreadLocalApi> entry : beansOfType.entrySet()) {
						RemoveThreadLocalApi removeThreadLocalApi = entry.getValue();
						removeThreadLocalApi.removeThreadLocalAction();
					}
				}
			}
			catch (Exception e) {
				// 清空失败
				log.error("清空threadLocal失败！", e);
			}
		}
	}

}
