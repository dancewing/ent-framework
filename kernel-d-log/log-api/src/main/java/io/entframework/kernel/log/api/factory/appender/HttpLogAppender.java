/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.api.factory.appender;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import io.entframework.kernel.rule.util.HttpServletUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 日志信息追加，用来追加http接口请求信息
 *
 * @date 2020/10/27 17:45
 */
public class HttpLogAppender {

	/**
	 * 追加请求信息到logRecordDTO
	 *
	 * @date 2020/10/27 18:22
	 */
	public static void appendHttpLog(LogRecordDTO logRecordDTO) {

		HttpServletRequest request;
		try {
			request = HttpServletUtil.getRequest();
		}
		catch (Exception e) {
			// 如果不是http环境，则直接返回
			return;
		}

		// 设置clientIp
		logRecordDTO.setClientIp(HttpServletUtil.getRequestClientIp(request));

		// 设置请求的url
		logRecordDTO.setRequestUrl(request.getServletPath());

		// 设置http的请求方法
		logRecordDTO.setHttpMethod(request.getMethod());

		// 解析http头，获取userAgent信息
		UserAgent userAgent = HttpServletUtil.getUserAgent(request);

		if (userAgent == null) {
			return;
		}

		// 设置浏览器标识
		if (ObjectUtil.isNotEmpty(userAgent.getBrowser())) {
			logRecordDTO.setClientBrowser(userAgent.getBrowser().getName());
		}

		// 设置浏览器操作系统
		if (ObjectUtil.isNotEmpty(userAgent.getOs())) {
			logRecordDTO.setClientOs(userAgent.getOs().getName());
		}

	}

}
