/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.sqladapter;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DatabaseExceptionEnum;
import io.entframework.kernel.rule.enums.DbTypeEnum;

import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * 异构sql获取基类，通过继承此类，编写使用不同数据库的sql
 *
 * @date 2020/10/31 23:44
 */
public abstract class AbstractSql {

	/**
	 * 根据jdbcUrl 判断数据库类型
	 * @param jdbcUrl
	 * @return
	 */
	public static String getDatabaseType(String jdbcUrl) {
		String dataSourceType = null;
		jdbcUrl = jdbcUrl.replace("\n", "").replace(" ", "").trim();
		Matcher matcher = compile("jdbc:\\w+").matcher(jdbcUrl);
		if (matcher.find()) {
			dataSourceType = matcher.group().split(":")[1];
		}
		return dataSourceType;
	}

	/**
	 * 获取异构sql
	 * @param jdbcUrl 数据连接的url
	 * @return 具体的sql
	 * @date 2020/10/31 23:44
	 */
	public String getSql(String jdbcUrl) {
		String databaseType = getDatabaseType(jdbcUrl);
		if (databaseType == null) {
			throw new DaoException(DatabaseExceptionEnum.DATABASE_TYPE_ERROR);
		}
		if (databaseType.contains(DbTypeEnum.ORACLE.getUrlWords())) {
			return oracle();
		}
		if (databaseType.contains(DbTypeEnum.DM.getUrlWords())) {
			return oracle();
		}
		if (databaseType.contains(DbTypeEnum.MS_SQL.getUrlWords())) {
			return sqlServer();
		}
		if (databaseType.contains(DbTypeEnum.PG_SQL.getUrlWords())) {
			return pgSql();
		}
		return mysql();
	}

	/**
	 * 获取mysql的sql语句
	 * @return 具体的sql
	 * @date 2020/10/31 23:45
	 */
	protected abstract String mysql();

	/**
	 * 获取sqlServer的sql语句
	 * @return 具体的sql
	 * @date 2020/10/31 23:45
	 */
	protected abstract String sqlServer();

	/**
	 * 获取pgSql的sql语句
	 * @return 具体的sql
	 * @date 2020/10/31 23:45
	 */
	protected abstract String pgSql();

	/**
	 * 获取oracle的sql语句
	 * @return 具体的sql
	 * @date 2020/10/31 23:45
	 */
	protected abstract String oracle();

}
