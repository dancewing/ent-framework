/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.controller;

import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.system.api.pojo.theme.DefaultTheme;
import io.entframework.kernel.system.api.pojo.theme.SysThemeRequest;
import io.entframework.kernel.system.modular.theme.service.SysThemeService;

/**
 * 主题开放接口的API
 *
 * @date 2022/1/10 18:27
 */
@RestController
@ApiResource(name = "主题开放接口的API")
public class SysThemeApiController {

	@Resource
	private SysThemeService sysThemeService;

	/**
	 * 获取当前Guns管理系统的主题数据
	 *
	 * @date 2022/1/10 18:29
	 */
	@GetResource(name = "获取当前Guns管理系统的主题数据", path = "/theme/current-theme-info", requiredPermission = false, requiredLogin = false)
	public ResponseData<DefaultTheme> currentThemeInfo(SysThemeRequest sysThemeParam) {
		DefaultTheme defaultTheme = sysThemeService.currentThemeInfo(sysThemeParam);
		return ResponseData.ok(defaultTheme);
	}

}
