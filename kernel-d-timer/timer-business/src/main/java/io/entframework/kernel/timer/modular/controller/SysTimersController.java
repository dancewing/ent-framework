/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.timer.modular.entity.SysTimers;
import io.entframework.kernel.timer.api.pojo.SysTimersRequest;
import io.entframework.kernel.timer.api.pojo.SysTimersResponse;
import io.entframework.kernel.timer.modular.service.SysTimersService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定时任务控制器
 *
 * @date 2020/10/27 14:30
 */
@RestController
@ApiResource(name = "定时任务管理")
public class SysTimersController {

	@Resource
	private SysTimersService sysTimersService;

	/**
	 * 添加定时任务
	 *
	 * @date 2020/6/30 18:26
	 */
	@PostResource(name = "添加定时任务", path = "/sys-timers/add")
	public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysTimersRequest sysTimersRequest) {
		sysTimersService.add(sysTimersRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 删除定时任务
	 *
	 * @date 2020/6/30 18:26
	 */
	@PostResource(name = "删除定时任务", path = "/sys-timers/delete")
	public ResponseData<Void> del(@RequestBody @Validated(BaseRequest.delete.class) SysTimersRequest sysTimersRequest) {
		sysTimersService.del(sysTimersRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 更新定时任务
	 *
	 * @date 2020/6/30 18:26
	 */
	@PostResource(name = "更新定时任务", path = "/sys-timers/update")
	public ResponseData<Void> update(
			@RequestBody @Validated(BaseRequest.update.class) SysTimersRequest sysTimersRequest) {
		sysTimersService.update(sysTimersRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 启动定时任务
	 *
	 * @date 2020/7/1 14:34
	 */
	@PostResource(name = "启动定时任务", path = "/sys-timers/start")
	public ResponseData<Void> start(
			@RequestBody @Validated(SysTimersRequest.startTimer.class) SysTimersRequest sysTimersRequest) {
		sysTimersService.start(sysTimersRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 停止定时任务
	 *
	 * @date 2020/7/1 14:34
	 */
	@PostResource(name = "停止定时任务", path = "/sys-timers/stop")
	public ResponseData<Void> stop(
			@RequestBody @Validated(SysTimersRequest.stopTimer.class) SysTimersRequest sysTimersRequest) {
		sysTimersService.stop(sysTimersRequest);
		return ResponseData.OK_VOID;
	}

	/**
	 * 查看详情定时任务
	 *
	 * @date 2020/6/30 18:26
	 */
	@GetResource(name = "查看详情定时任务", path = "/sys-timers/detail")
	public ResponseData<SysTimers> detail(@Validated(BaseRequest.detail.class) SysTimersRequest sysTimersRequest) {
		return ResponseData.ok(sysTimersService.detail(sysTimersRequest));
	}

	/**
	 * 分页查询定时任务
	 *
	 * @date 2020/6/30 18:26
	 */
	@GetResource(name = "分页查询定时任务", path = "/sys-timers/page")
	public ResponseData<PageResult<SysTimersResponse>> page(SysTimersRequest sysTimersRequest) {
		return ResponseData.ok(sysTimersService.findPage(sysTimersRequest));
	}

	/**
	 * 获取全部定时任务
	 *
	 * @date 2020/6/30 18:26
	 */
	@GetResource(name = "获取全部定时任务", path = "/sys-timers/list")
	public ResponseData<List<SysTimersResponse>> list(SysTimersRequest sysTimersRequest) {
		return ResponseData.ok(sysTimersService.findList(sysTimersRequest));
	}

	/**
	 * 获取系统的所有任务列表
	 *
	 * @date 2020/7/1 14:34
	 */
	@PostResource(name = "获取系统的所有任务列表", path = "/sys-timers/get-action-classes")
	public ResponseData<List<String>> getActionClasses() {
		List<String> actionClasses = sysTimersService.getActionClasses();
		return ResponseData.ok(actionClasses);
	}

}
