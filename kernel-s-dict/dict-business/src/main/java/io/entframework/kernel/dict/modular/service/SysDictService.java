/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.dict.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.dict.api.DictApi;
import io.entframework.kernel.dict.modular.entity.SysDict;
import io.entframework.kernel.dict.modular.pojo.TreeDictInfo;
import io.entframework.kernel.dict.modular.pojo.request.SysDictRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictResponse;

import java.util.List;

/**
 * 字典详情管理
 *
 * @date 2020/10/29 17:43
 */
public interface SysDictService extends BaseService<SysDictRequest, SysDictResponse, SysDict>, DictApi {

	/**
	 * 新增字典
	 * @param SysDictRequest 字典对象
	 * @date 2020/10/29 17:43
	 */
	void add(SysDictRequest SysDictRequest);

	/**
	 * 删除字典
	 * @param SysDictRequest 字典对象
	 * @date 2020/10/29 17:43
	 */
	void del(SysDictRequest SysDictRequest);

	/**
	 * 修改字典
	 * @param SysDictRequest 字典对象
	 * @date 2020/10/29 17:43
	 */
	SysDictResponse update(SysDictRequest SysDictRequest);

	/**
	 * 查询字典详情
	 * @param SysDictRequest 字典id
	 * @return 字典的详情
	 * @date 2020/10/30 16:15
	 */
	SysDictResponse detail(SysDictRequest SysDictRequest);

	/**
	 * 获取字典列表
	 * @param SysDictRequest 字典对象
	 * @return 字典列表
	 * @date 2020/10/29 18:48
	 */
	List<SysDictResponse> findList(SysDictRequest SysDictRequest);

	/**
	 * 获取字典列表（带分页）
	 * @param SysDictRequest 查询条件
	 * @return 带分页的列表
	 * @date 2020/10/29 18:48
	 */
	PageResult<SysDictResponse> findPage(SysDictRequest SysDictRequest);

	/**
	 * 获取树形字典列表（antdv在用）
	 * @param SysDictRequest 查询条件
	 * @return 字典信息列表
	 * @date 2020/10/29 18:50
	 */
	List<TreeDictInfo> getTreeDictList(SysDictRequest SysDictRequest);

}