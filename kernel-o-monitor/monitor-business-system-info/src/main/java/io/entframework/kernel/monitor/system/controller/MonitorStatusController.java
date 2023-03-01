/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.monitor.system.controller;

import io.entframework.kernel.monitor.system.SystemHardwareCalculator;
import io.entframework.kernel.monitor.system.holder.SystemHardwareInfoHolder;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 系统监控控制器
 *
 * @date 2021/4/14 18:44
 */
@RestController
@ApiResource(name = "监控的控制器")
public class MonitorStatusController {

	@Resource
	private SystemHardwareInfoHolder systemHardwareInfoHolder;

	/**
	 * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
	 *
	 * @date 2020/6/29 16:49
	 */
	@GetResource(name = "获取系统信息", path = "/system-info")
	public ResponseData<SystemHardwareCalculator> getSystemInfo() {
		SystemHardwareCalculator systemHardwareInfo = systemHardwareInfoHolder.getSystemHardwareInfo();
		return ResponseData.ok(systemHardwareInfo);
	}

}
