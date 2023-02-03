/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.notice.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeRequest;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeResponse;
import io.entframework.kernel.system.modular.notice.service.SysNoticeService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通知管理控制器
 *
 * @date 2021/1/8 19:47
 */
@RestController
@ApiResource(name = "通知管理")
public class SysNoticeController {

	@Resource
	private SysNoticeService sysNoticeService;

	/**
	 * 添加通知管理
	 *
	 * @date 2021/1/9 14:44
	 */
	@PostResource(name = "添加通知管理", path = "/sys-notice/add")
	public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysNoticeRequest sysNoticeParam) {
		sysNoticeService.add(sysNoticeParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 更新通知管理
	 *
	 * @date 2021/1/9 14:54
	 */
	@PostResource(name = "更新通知管理", path = "/sys-notice/update")
	public ResponseData<Void> update(
			@RequestBody @Validated(BaseRequest.update.class) SysNoticeRequest sysNoticeParam) {
		sysNoticeService.update(sysNoticeParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除通知管理
	 *
	 * @date 2021/1/9 14:54
	 */
	@PostResource(name = "删除通知管理", path = "/sys-notice/delete")
	public ResponseData<Void> delete(
			@RequestBody @Validated(BaseRequest.delete.class) SysNoticeRequest sysNoticeParam) {
		sysNoticeService.del(sysNoticeParam);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查看通知管理
	 *
	 * @date 2021/1/9 9:49
	 */
	@GetResource(name = "查看通知管理", path = "/sys-notice/detail")
	public ResponseData<SysNoticeResponse> detail(
			@Validated(BaseRequest.detail.class) SysNoticeRequest sysNoticeParam) {
		return ResponseData.ok(sysNoticeService.detail(sysNoticeParam));
	}

	/**
	 * 查询通知管理
	 *
	 * @date 2021/1/9 21:23
	 */
	@GetResource(name = "查询通知管理", path = "/sys-notice/page")
	public ResponseData<PageResult<SysNoticeResponse>> page(SysNoticeRequest sysNoticeParam) {
		return ResponseData.ok(sysNoticeService.findPage(sysNoticeParam));
	}

	/**
	 * 通知管理列表
	 *
	 * @date 2021/1/9 14:55
	 */
	@GetResource(name = "通知管理列表", path = "/sys-notice/list")
	public ResponseData<List<SysNoticeResponse>> list(SysNoticeRequest sysNoticeParam) {
		return ResponseData.ok(sysNoticeService.findList(sysNoticeParam));
	}

}
