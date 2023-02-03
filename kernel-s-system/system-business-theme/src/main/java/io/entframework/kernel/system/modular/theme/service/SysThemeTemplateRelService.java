/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service;

import io.entframework.kernel.db.mds.service.BaseService;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;

import java.util.List;

/**
 * 系统主题模板属性关系service接口
 *
 * @date 2021/12/17 16:13
 */
public interface SysThemeTemplateRelService extends BaseService<SysThemeTemplateRelRequest, SysThemeTemplateRelResponse, SysThemeTemplateRel> {

	/**
	 * 增加系统主题模板属性关系
	 *
	 * @date 2021/12/24 10:56
	 */
	void add(SysThemeTemplateRelRequest sysThemeTemplateRelRequest);

	/**
	 * 删除系统主题模板属性关系
	 *
	 * @date 2021/12/24 11:18
	 */
	void del(SysThemeTemplateRelRequest sysThemeTemplateRelRequest);

	List<SysThemeTemplateRel> select(SelectDSLCompleter completer);

	int delete(DeleteDSLCompleter completer);
}
