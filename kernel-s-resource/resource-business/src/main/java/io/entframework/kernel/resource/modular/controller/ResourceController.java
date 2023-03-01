/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.resource.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.resource.api.pojo.SysResourceResponse;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.resource.api.pojo.SysResourceRequest;
import io.entframework.kernel.resource.modular.service.SysResourceService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 资源管理控制器
 *
 * @date 2020/11/24 19:47
 */
@RestController
@ApiResource(name = "资源管理")
public class ResourceController {

	@Resource
	private SysResourceService sysResourceService;

	/**
	 * 获取资源列表
	 *
	 * @date 2020/11/24 19:47
	 */
	@GetResource(name = "获取资源列表", path = "/resource/page")
	public ResponseData<PageResult<SysResourceResponse>> pageList(SysResourceRequest sysResourceRequest) {
		PageResult<SysResourceResponse> result = this.sysResourceService.findPage(sysResourceRequest);
		return ResponseData.ok(result);
	}

	/**
	 * 获取资源下拉列表（获取菜单资源）
	 *
	 * @date 2020/11/24 19:51
	 */
	@GetResource(name = "获取资源下拉列表", path = "/resource/get-menu-resource-list")
	public ResponseData<List<SysResourceResponse>> getMenuResourceList(SysResourceRequest sysResourceRequest) {
		List<SysResourceResponse> menuResourceList = this.sysResourceService.findList(sysResourceRequest);
		return ResponseData.ok(menuResourceList);
	}

}
