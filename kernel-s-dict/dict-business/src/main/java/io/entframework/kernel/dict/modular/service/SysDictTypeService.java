/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.dict.modular.entity.SysDictType;
import io.entframework.kernel.dict.modular.pojo.request.SysDictTypeRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictTypeResponse;

import java.util.List;

public interface SysDictTypeService extends BaseService<SysDictTypeRequest, SysDictTypeResponse, SysDictType> {

	/**
	 * 添加字典类型
	 * @param dictTypeRequest 字典类型请求
	 * @date 2020/10/29 18:55
	 */
	void add(SysDictTypeRequest dictTypeRequest);

	/**
	 * 删除字典类型
	 * @param dictTypeRequest 字典类型请求
	 * @date 2020/10/29 18:55
	 */
	void del(SysDictTypeRequest dictTypeRequest);

	/**
	 * 修改字典类型
	 * @param dictTypeRequest 字典类型请求
	 * @date 2020/10/29 18:55
	 */
	SysDictTypeResponse update(SysDictTypeRequest dictTypeRequest);

	/**
	 * 修改字典状态
	 * @param dictTypeRequest 字典类型请求
	 * @date 2020/10/29 18:56
	 */
	void updateStatus(SysDictTypeRequest dictTypeRequest);

	/**
	 * 查询-详情-按实体对象
	 * @param dictTypeRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	SysDictTypeResponse detail(SysDictTypeRequest dictTypeRequest);

	/**
	 * 获取字典类型列表
	 * @param dictTypeRequest 字典类型请求
	 * @return 字典类型列表
	 * @date 2020/10/29 18:55
	 */
	List<SysDictTypeResponse> findList(SysDictTypeRequest dictTypeRequest);

	/**
	 * 获取字典类型列表（带分页）
	 * @param dictTypeRequest 字典类型请求
	 * @return 字典类型列表
	 * @date 2020/10/29 18:55
	 */
	PageResult<SysDictTypeResponse> findPage(SysDictTypeRequest dictTypeRequest);

}