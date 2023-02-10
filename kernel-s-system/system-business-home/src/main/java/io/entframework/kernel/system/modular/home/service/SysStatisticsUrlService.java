/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrl;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsUrlRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsUrlResponse;

import java.util.List;

/**
 * 常用功能列表 服务类
 *
 * @date 2022/02/10 21:17
 */
public interface SysStatisticsUrlService extends BaseService<SysStatisticsUrlRequest, SysStatisticsUrlResponse, SysStatisticsUrl> {

	/**
	 * 新增
	 *
	 * @param sysStatisticsUrlRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	void add(SysStatisticsUrlRequest sysStatisticsUrlRequest);

	/**
	 * 删除
	 *
	 * @param sysStatisticsUrlRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	void del(SysStatisticsUrlRequest sysStatisticsUrlRequest);

	/**
	 * 编辑
	 *
	 * @param sysStatisticsUrlRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	void edit(SysStatisticsUrlRequest sysStatisticsUrlRequest);

	/**
	 * 查询详情
	 *
	 * @param sysStatisticsUrlRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	SysStatisticsUrlResponse detail(SysStatisticsUrlRequest sysStatisticsUrlRequest);

	/**
	 * 获取列表
	 *
	 * @param sysStatisticsUrlRequest 请求参数
	 * @return List<SysStatisticsUrl> 返回结果
	 * @date 2022/02/10 21:17
	 */
	List<SysStatisticsUrlResponse> findList(SysStatisticsUrlRequest sysStatisticsUrlRequest);

	/**
	 * 获取列表（带分页）
	 *
	 * @param sysStatisticsUrlRequest 请求参数
	 * @return PageResult<SysStatisticsUrl> 返回结果
	 * @date 2022/02/10 21:17
	 */
	PageResult<SysStatisticsUrlResponse> findPage(SysStatisticsUrlRequest sysStatisticsUrlRequest);

	List<Long> getMenuIdsByStatUrlIdList(List<Long> statUrlIds);
}
