/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.config.api;

import cn.hutool.db.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 系统配置元数据获取的api
 *
 * @date 2021/3/27 21:15
 */
public interface SysConfigDataApi {

	/**
	 * 获取系统配置表中的所有数据
	 * @param conn 原始数据库连接
	 * @return 所有记录的list
	 * @date 2021/3/27 21:15
	 */
	List<Entity> getConfigs(Connection conn) throws SQLException;

	/**
	 * 获取所有配置list的sql
	 *
	 * @date 2021/3/27 21:19
	 */
	String getConfigListSql();

}
