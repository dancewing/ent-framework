/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.config.modular.sqladapter;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import io.entframework.kernel.config.api.SysConfigDataApi;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Oracle数据库的系统配置表获取
 *
 * @date 2021/5/14 21:18
 */
@Slf4j
public class OracleSysConfigData implements SysConfigDataApi {

	@Override
	public List<Entity> getConfigs(Connection conn) throws SQLException {
		return SqlExecutor.query(conn, getConfigListSql(), new EntityListHandler(), StatusEnum.ENABLE.getValue(),
				YesOrNotEnum.N.getValue());
	}

	@Override
	public String getConfigListSql() {
		return "select config_code as \"config_code\", config_value as \"config_value\" from sys_config where status_flag = ? and del_flag = ?";
	}

}
