/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.sqladapter.table;

import io.entframework.kernel.db.api.sqladapter.AbstractSql;
import lombok.Getter;

/**
 * 获取某个表的所有字段的sql
 *
 * @date 2019-07-16-13:06
 */
@Getter
public class TableFieldListSql extends AbstractSql {

	@Override
	protected String mysql() {
		return "select COLUMN_NAME as columnName,COLUMN_COMMENT as columnComment from information_schema.COLUMNS where table_name = ? and table_schema = ?";
	}

	@Override
	protected String sqlServer() {
		return """
				SELECT A.name as columnName
				      CONVERT(varchar(200), isnull(G.[value], '')) as columnComment
				FROM syscolumns A
				   Left Join systypes B On A.xusertype= B.xusertype
				   Inner Join sysobjects D On A.id= D.id
				      and D.xtype= 'U'
				      and D.name<> 'dtproperties'
				   Left Join syscomments E on A.cdefault= E.id
				   Left Join sys.extended_properties G on A.id= G.major_id
				      and A.colid= G.minor_id
				   Left Join sys.extended_properties F On D.id= F.major_id
				      and F.minor_id= 0
				where d.name= ?
				Order By A.id,A.colorder
				""";
	}

	@Override
	protected String pgSql() {
		return "SELECT a.attname as \"columnName\" , col_description(a.attrelid,a.attnum) as \"columnComment\"\n"
				+ "FROM pg_class as c,pg_attribute as a " + "where c.relname = ? and a.attrelid = c.oid and a.attnum>0";
	}

	@Override
	protected String oracle() {
		return "select column_name as columnName, comments as columnComment from user_col_comments where Table_Name= ?";
	}

}
