/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.annotation.BusinessLog;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateResponse;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统主题模板控制器
 *
 * @date 2021/12/17 13:53
 */
@RestController
@ApiResource(name = "系统主题模板管理")
public class SysThemeTemplateController {

	@Resource
	private SysThemeTemplateService sysThemeTemplateService;

	/**
	 * 增加系统主题模板
	 *
	 * @date 2021/12/17 14:16
	 */
	@PostResource(name = "增加系统主题模板", path = "/sys-theme-template/add")
	@BusinessLog
	public ResponseData<Void> add(
			@RequestBody @Validated(BaseRequest.add.class) SysThemeTemplateRequest sysThemeTemplateParam) {
		sysThemeTemplateService.add(sysThemeTemplateParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 更新系统主题模板
	 *
	 * @date 2021/12/17 14:36
	 */
	@PostResource(name = "更新系统主题模板", path = "/sys-theme-template/update")
	@BusinessLog
	public ResponseData<Void> update(
			@RequestBody @Validated(BaseRequest.update.class) SysThemeTemplateRequest sysThemeTemplateParam) {
		sysThemeTemplateService.update(sysThemeTemplateParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除系统主题模板
	 *
	 * @date 2021/12/17 14:47
	 */
	@PostResource(name = "删除系统主题模板", path = "/sys-theme-template/del")
	@BusinessLog
	public ResponseData<Void> del(
			@RequestBody @Validated(BaseRequest.delete.class) SysThemeTemplateRequest sysThemeTemplateParam) {
		sysThemeTemplateService.del(sysThemeTemplateParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查询系统主题模板
	 *
	 * @date 2021/12/17 15:00
	 */
	@GetResource(name = "查询系统主题模板", path = "/sys-theme-template/page")
	public ResponseData<PageResult<SysThemeTemplateResponse>> findPage(SysThemeTemplateRequest sysThemeTemplateParam) {
		return ResponseData.ok(sysThemeTemplateService.findPage(sysThemeTemplateParam));
	}

	/**
	 * 查询系统主题模板列表
	 *
	 * @date 2021/12/29 9:12
	 */
	@GetResource(name = "查询系统主题模板列表", path = "/sys-theme-template/list")
	public ResponseData<List<SysThemeTemplateResponse>> findList(SysThemeTemplateRequest sysThemeTemplateParam) {
		return ResponseData.ok(sysThemeTemplateService.findList(sysThemeTemplateParam));
	}

	/**
	 * 修改系统主题模板状态
	 *
	 * @date 2021/12/17 15:09
	 */
	@PostResource(name = "修改系统主题模板状态", path = "/sys-theme-template/update-status")
	@BusinessLog
	public ResponseData<Void> updateTemplateStatus(
			@RequestBody @Validated(BaseRequest.updateStatus.class) SysThemeTemplateRequest sysThemeTemplateParam) {
		sysThemeTemplateService.updateTemplateStatus(sysThemeTemplateParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查询系统主题模板详情
	 *
	 * @date 2021/12/17 16:09
	 */
	@GetResource(name = "查询系统主题模板详情", path = "/sys-theme-template/detail")
	public ResponseData<List<SysThemeTemplateFieldResponse>> detail(SysThemeTemplateRequest sysThemeTemplateParam) {
		return ResponseData.ok(sysThemeTemplateService.detail(sysThemeTemplateParam));
	}
}
