/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.timer.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.timer.api.pojo.SysTimersRequest;
import io.entframework.kernel.timer.api.pojo.SysTimersResponse;
import io.entframework.kernel.timer.modular.entity.SysTimers;

import java.util.List;

/**
 * 定时任务 服务类
 *
 * @date 2020/6/30 18:26
 */
public interface SysTimersService extends BaseService<SysTimersRequest, SysTimersResponse, SysTimers> {

	/**
	 * 添加定时任务
	 * @param sysTimersRequest 添加参数
	 * @date 2020/6/30 18:26
	 */
	void add(SysTimersRequest sysTimersRequest);

	/**
	 * 删除定时任务
	 * @param sysTimersRequest 删除参数
	 * @date 2020/6/30 18:26
	 */
	void del(SysTimersRequest sysTimersRequest);

	/**
	 * 编辑定时任务
	 * @param sysTimersRequest 编辑参数
	 * @date 2020/6/30 18:26
	 */
	SysTimersResponse update(SysTimersRequest sysTimersRequest);

	/**
	 * 启动任务
	 * @param sysTimersRequest 启动参数
	 * @date 2020/7/1 14:36
	 */
	void start(SysTimersRequest sysTimersRequest);

	/**
	 * 停止任务
	 * @param sysTimersRequest 停止参数
	 * @date 2020/7/1 14:36
	 */
	void stop(SysTimersRequest sysTimersRequest);

	/**
	 * 查看详情定时任务
	 * @param sysTimersRequest 查看参数
	 * @return 定时任务
	 * @date 2020/6/30 18:26
	 */
	SysTimers detail(SysTimersRequest sysTimersRequest);

	/**
	 * 分页查询定时任务
	 * @param sysTimersRequest 查询参数
	 * @return 查询分页结果
	 * @date 2020/6/30 18:26
	 */
	PageResult<SysTimersResponse> findPage(SysTimersRequest sysTimersRequest);

	/**
	 * 查询所有定时任务
	 * @param sysTimersRequest 查询参数
	 * @return 定时任务列表
	 * @date 2020/6/30 18:26
	 */
	List<SysTimersResponse> findList(SysTimersRequest sysTimersRequest);

	/**
	 * 获取所有可执行的任务列表
	 * @return TimerTaskRunner的所有子类名称集合
	 * @date 2020/7/1 14:36
	 */
	List<String> getActionClasses();

}
