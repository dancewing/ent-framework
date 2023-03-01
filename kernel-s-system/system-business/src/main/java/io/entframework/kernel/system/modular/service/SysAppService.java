/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.AppServiceApi;
import io.entframework.kernel.system.api.pojo.request.SysAppRequest;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;
import io.entframework.kernel.system.modular.entity.SysApp;

import java.util.List;

public interface SysAppService extends BaseService<SysAppRequest, SysAppResponse, SysApp>, AppServiceApi {

	/**
	 * 添加系统应用
	 * @param sysAppParam 添加参数
	 * @date 2020/3/25 14:57
	 */
	void add(SysAppRequest sysAppParam);

	/**
	 * 删除系统应用
	 * @param sysAppParam 删除参数
	 * @date 2020/3/25 14:57
	 */
	void del(SysAppRequest sysAppParam);

	/**
	 * 编辑系统应用
	 * @param sysAppParam 编辑参数
	 * @date 2020/3/25 14:58
	 */
	SysAppResponse update(SysAppRequest sysAppParam);

	/**
	 * 更新状态
	 * @param sysAppParam 请求参数
	 * @date 2021/1/6 14:30
	 */
	void updateStatus(SysAppRequest sysAppParam);

	/**
	 * 查看系统应用
	 * @param sysAppParam 查看参数
	 * @return 系统应用
	 * @date 2020/3/26 9:50
	 */
	SysAppResponse detail(SysAppRequest sysAppParam);

	/**
	 * 系统应用列表
	 * @param sysAppParam 查询参数
	 * @return 系统应用列表
	 * @date 2020/4/19 14:56
	 */
	List<SysAppResponse> findList(SysAppRequest sysAppParam);

	/**
	 * 查询系统应用
	 * @param sysAppParam 查询参数
	 * @return 查询分页结果
	 * @date 2020/3/24 20:55
	 */
	PageResult<SysAppResponse> findPage(SysAppRequest sysAppParam);

	/**
	 * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
	 * @param sysAppParam 设为默认应用参数
	 * @date 2020/6/29 16:49
	 */
	void updateActiveFlag(SysAppRequest sysAppParam);

	/**
	 * 获取用户的顶部app导航列表
	 *
	 * @date 2021/4/21 15:34
	 */
	List<SysApp> getUserTopAppList();

}