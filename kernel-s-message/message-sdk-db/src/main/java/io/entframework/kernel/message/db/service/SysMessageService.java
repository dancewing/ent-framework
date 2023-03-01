/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.db.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.message.api.pojo.request.SysMessageRequest;
import io.entframework.kernel.message.api.pojo.response.SysMessageResponse;
import io.entframework.kernel.message.db.entity.SysMessage;

import java.util.List;

/**
 * 系统消息 service接口
 *
 * @date 2020/12/31 20:09
 */
public interface SysMessageService extends BaseService<SysMessageRequest, SysMessageResponse, SysMessage> {

	/**
	 * 新增
	 * @param sysMessageRequest 参数对象
	 * @date 2021/2/2 20:48
	 */
	void add(SysMessageRequest sysMessageRequest);

	/**
	 * 删除
	 * @param sysMessageRequest 参数对象
	 * @date 2021/1/26 12:52
	 */
	void del(SysMessageRequest sysMessageRequest);

	/**
	 * 修改
	 * @param sysMessageRequest 参数对象
	 * @date 2021/2/2 20:48
	 */
	SysMessageResponse update(SysMessageRequest sysMessageRequest);

	/**
	 * 查询-详情-根据主键id
	 * @param sysMessageRequest 参数对象
	 * @date 2021/2/2 20:48
	 */
	SysMessageResponse detail(SysMessageRequest sysMessageRequest);

	/**
	 * 分页查询
	 * @param sysMessageRequest 参数
	 * @date 2021/2/2 20:48
	 */
	PageResult<SysMessageResponse> findPage(SysMessageRequest sysMessageRequest);

	/**
	 * 列表查询
	 * @param sysMessageRequest 参数
	 * @date 2021/1/8 15:21
	 */
	List<SysMessageResponse> findList(SysMessageRequest sysMessageRequest);

	/**
	 * 数量查询
	 * @param sysMessageRequest 参数
	 * @date 2021/1/11 19:21
	 */
	long findCount(SysMessageRequest sysMessageRequest);

	void update(SysMessage sysMessage);

	List<SysMessage> batchCreateEntity(List<SysMessage> records);

}
