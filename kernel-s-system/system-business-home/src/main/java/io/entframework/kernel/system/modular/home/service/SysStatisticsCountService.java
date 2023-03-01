/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCount;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsCountRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsCountResponse;

import java.util.List;

/**
 * 常用功能的统计次数 服务类
 *
 * @date 2022/02/10 21:17
 */
public interface SysStatisticsCountService
		extends BaseService<SysStatisticsCountRequest, SysStatisticsCountResponse, SysStatisticsCount> {

	/**
	 * 新增
	 * @param sysStatisticsCountRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	void add(SysStatisticsCountRequest sysStatisticsCountRequest);

	/**
	 * 删除
	 * @param sysStatisticsCountRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	void del(SysStatisticsCountRequest sysStatisticsCountRequest);

	/**
	 * 编辑
	 * @param sysStatisticsCountRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	SysStatisticsCountResponse update(SysStatisticsCountRequest sysStatisticsCountRequest);

	/**
	 * 查询详情
	 * @param sysStatisticsCountRequest 请求参数
	 * @date 2022/02/10 21:17
	 */
	SysStatisticsCountResponse detail(SysStatisticsCountRequest sysStatisticsCountRequest);

	/**
	 * 获取列表
	 * @param sysStatisticsCountRequest 请求参数
	 * @return List<SysStatisticsCount> 返回结果
	 * @date 2022/02/10 21:17
	 */
	List<SysStatisticsCountResponse> findList(SysStatisticsCountRequest sysStatisticsCountRequest);

	/**
	 * 获取列表（带分页）
	 * @param sysStatisticsCountRequest 请求参数
	 * @return PageResult<SysStatisticsCount> 返回结果
	 * @date 2022/02/10 21:17
	 */
	PageResult<SysStatisticsCountResponse> findPage(SysStatisticsCountRequest sysStatisticsCountRequest);

	/**
	 * 获取某个用户的统计次数
	 *
	 * @date 2022/2/10 21:56
	 */
	Integer getUserUrlCount(Long userId, Long statUrlId);

}
