/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.controller;

import java.util.List;

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
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateFieldService;

/**
 * 系统主题模板属性控制器
 *
 * @date 2021/12/17 10:28
 */
@RestController
@ApiResource(name = "系统主题模板属性管理")
public class SysThemeTemplateFieldController {

	@Resource
	private SysThemeTemplateFieldService sysThemeTemplateFieldService;

	/**
	 * 增加系统主题模板属性
	 *
	 * @date 2021/12/17 11:22
	 */
	@PostResource(name = "增加系统主题模板属性", path = "/sys-theme-template-field/add")
	@BusinessLog
	public ResponseData<Void> add(
			@RequestBody @Validated(BaseRequest.add.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		sysThemeTemplateFieldService.add(sysThemeTemplateFieldParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除系统主题模板属性
	 *
	 * @date 2021/12/17 11:25
	 */
	@PostResource(name = "删除系统主题模板属性", path = "/sys-theme-template-field/del")
	@BusinessLog
	public ResponseData<Void> del(
			@RequestBody @Validated(BaseRequest.delete.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		sysThemeTemplateFieldService.del(sysThemeTemplateFieldParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 修改系统主题模板属性
	 *
	 * @date 2021/12/17 11:38
	 */
	@PostResource(name = "修改系统模板属性", path = "/sys-theme-template-field/update")
	@BusinessLog
	public ResponseData<Void> update(
			@RequestBody @Validated(BaseRequest.update.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		sysThemeTemplateFieldService.update(sysThemeTemplateFieldParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查询系统主题模板属性详情
	 *
	 * @date 2021/12/17 11:49
	 */
	@GetResource(name = "查询系统主题模板属性详情", path = "/sys-theme-template-field/detail")
	public ResponseData<SysThemeTemplateFieldResponse> detail(
			@Validated(BaseRequest.detail.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		return ResponseData.ok(sysThemeTemplateFieldService.detail(sysThemeTemplateFieldParam));
	}

	/**
	 * 查询系统主题模板属性列表
	 *
	 * @date 2021/12/24 9:47
	 */
	@GetResource(name = "查询系统主题模板属性列表", path = "/sys-theme-template-field/find-page")
	public ResponseData<PageResult<SysThemeTemplateFieldResponse>> findPage(
			SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		return ResponseData.ok(sysThemeTemplateFieldService.findPage(sysThemeTemplateFieldParam));
	}

	/**
	 * 查询系统主题模板属性已有关系列表
	 *
	 * @date 2021/12/24 14:42
	 */
	@GetResource(name = "查询系统主题模板属性已有关系列表", path = "/sys-theme-template-field/find-rel-list")
	public ResponseData<List<SysThemeTemplateFieldResponse>> findRelPage(
			SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		return ResponseData.ok(sysThemeTemplateFieldService.findRelList(sysThemeTemplateFieldParam));
	}

	/**
	 * 查询系统主题模板属性未有关系列表
	 *
	 * @date 2021/12/24 14:44
	 */
	@GetResource(name = "查询系统主题模板属性未有关系列表", path = "/sys-theme-template-field/find-not-rel-list")
	public ResponseData<List<SysThemeTemplateFieldResponse>> findNotRelPage(
			SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
		return ResponseData.ok(sysThemeTemplateFieldService.findNotRelList(sysThemeTemplateFieldParam));
	}

}
