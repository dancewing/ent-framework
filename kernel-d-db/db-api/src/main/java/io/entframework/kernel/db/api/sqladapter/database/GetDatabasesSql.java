/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.sqladapter.database;

import io.entframework.kernel.db.api.sqladapter.AbstractSql;
import lombok.Getter;

/**
 * 创建数据库的sql，可用在租户的创建
 *
 * @date 2019-07-16-13:06
 */
@Getter
public class GetDatabasesSql extends AbstractSql {

	@Override
	protected String mysql() {
		return "show databases;";
	}

	@Override
	protected String sqlServer() {
		return "";
	}

	@Override
	protected String pgSql() {
		return "";
	}

	@Override
	protected String oracle() {
		return "";
	}

}
