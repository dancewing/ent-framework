/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.controller;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.annotation.BusinessLog;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.system.api.pojo.theme.SysThemeRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeResponse;
import io.entframework.kernel.system.modular.theme.service.SysThemeService;

/**
 * 系统主题控制器
 *
 * @date 2021/12/17 16:40
 */
@RestController
@ApiResource(name = "系统主题管理")
public class SysThemeController {

	@Resource
	private SysThemeService sysThemeService;

	/**
	 * 增加系统主题
	 *
	 * @date 2021/12/17 16:43
	 */
	@PostResource(name = "增加系统主题", path = "/sys-theme/add")
	@BusinessLog
	public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysThemeRequest sysThemeParam) {
		sysThemeService.add(sysThemeParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除系统主题
	 *
	 * @date 2021/12/17 16:45
	 */
	@PostResource(name = "删除系统主题", path = "/sys-theme/del")
	@BusinessLog
	public ResponseData<Void> del(@RequestBody @Validated(BaseRequest.delete.class) SysThemeRequest sysThemeParam) {
		sysThemeService.del(sysThemeParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 修改系统主题
	 *
	 * @date 2021/12/17 16:50
	 */
	@PostResource(name = "修改系统主题", path = "/sys-theme/update")
	@BusinessLog
	public ResponseData<Void> update(@RequestBody @Validated(BaseRequest.update.class) SysThemeRequest sysThemeParam) {
		sysThemeService.update(sysThemeParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查询系统主题
	 *
	 * @date 2021/12/17 16:58
	 */
	@GetResource(name = "查询系统主题", path = "/sys-theme/find-page")
	public ResponseData<PageResult<SysThemeResponse>> findPage(SysThemeRequest sysThemeParam) {
		return ResponseData.ok(sysThemeService.findPage(sysThemeParam));
	}

	/**
	 * 查询系统主题详情
	 *
	 * @date 2021/12/17 17:04
	 */
	@GetResource(name = "查询系统主题详情", path = "/sys-theme/detail")
	public ResponseData<SysThemeResponse> detail(
			@Validated(BaseRequest.updateStatus.class) SysThemeRequest sysThemeParam) {
		return ResponseData.ok(sysThemeService.detail(sysThemeParam));
	}

	/**
	 * 修改系统主题启用状态
	 *
	 * @date 2021/12/17 17:32
	 */
	@PostResource(name = "修改系统主题启用状态", path = "/sys-theme/update-status")
	@BusinessLog
	public ResponseData<Void> updateThemeStatus(
			@RequestBody @Validated(BaseRequest.updateStatus.class) SysThemeRequest sysThemeParam) {
		sysThemeService.updateThemeStatus(sysThemeParam);
		return ResponseData.OK_VOID;
	}
}
