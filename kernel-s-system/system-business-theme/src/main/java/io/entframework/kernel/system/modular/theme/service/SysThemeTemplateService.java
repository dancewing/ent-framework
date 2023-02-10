/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplate;

import java.util.List;

/**
 * 系统主题模板service接口
 *
 * @date 2021/12/17 13:55
 */
public interface SysThemeTemplateService extends BaseService<SysThemeTemplateRequest, SysThemeTemplateResponse, SysThemeTemplate> {

	/**
	 * 增加系统主题模板
	 *
	 * @date 2021/12/17 14:04
	 */
	void add(SysThemeTemplateRequest sysThemeTemplateRequest);

	/**
	 * 编辑系统主题模板
	 *
	 * @date 2021/12/17 14:21
	 */
	SysThemeTemplateResponse update(SysThemeTemplateRequest sysThemeTemplateRequest);

	/**
	 * 删除系统主题模板
	 *
	 * @date 2021/12/17 14:38
	 */
	void del(SysThemeTemplateRequest sysThemeTemplateRequest);

	/**
	 * 查询系统主题模板列表
	 *
	 * @return 分页结果
	 * @date 2021/12/17 14:52
	 */
	PageResult<SysThemeTemplateResponse> findPage(SysThemeTemplateRequest sysThemeTemplateRequest);

	/**
	 * 查询系统诸如提模板列表
	 *
	 * @date 2021/12/29 9:10
	 */
	List<SysThemeTemplateResponse> findList(SysThemeTemplateRequest sysThemeTemplateRequest);

	/**
	 * 修改系统主题模板状态
	 *
	 * @date 2021/12/17 15:03
	 */
	void updateTemplateStatus(SysThemeTemplateRequest sysThemeTemplateRequest);

	/**
	 * 查询系统主题模板详情
	 *
	 * @date 2021/12/17 16:00
	 */
	List<SysThemeTemplateFieldResponse> detail(SysThemeTemplateRequest sysThemeTemplateRequest);
}
