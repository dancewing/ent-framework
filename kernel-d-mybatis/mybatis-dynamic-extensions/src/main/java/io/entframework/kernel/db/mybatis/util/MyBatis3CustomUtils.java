/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mybatis.util;

import io.entframework.kernel.db.mybatis.dynamic.insert.InsertIgnoreDSL;
import io.entframework.kernel.db.mybatis.dynamic.insert.MultiRowIgnoreInsertDSL;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import java.util.Collection;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

/**
 * author: jeff_qian
 */
public class MyBatis3CustomUtils {

	/**
	 * mysql的insert ignore into语句, 使用时先确认数据库是否支持该语法
	 */
	public static <R> int ignoreInsert(ToIntFunction<InsertStatementProvider<R>> mapper, R row, SqlTable table,
			UnaryOperator<InsertIgnoreDSL<R>> completer) {
		return mapper.applyAsInt(ignoreInsert(row, table, completer));
	}

	/**
	 * mysql的insert ignore into语句, 使用时先确认数据库是否支持该语法
	 */
	public static <R> InsertStatementProvider<R> ignoreInsert(R row, SqlTable table,
			UnaryOperator<InsertIgnoreDSL<R>> completer) {
		return completer.apply(InsertIgnoreDSL.insert(row).into(table)).build().render(RenderingStrategies.MYBATIS3);
	}

	/**
	 * mysql的insert ignore into语句, 使用时先确认数据库是否支持该语法, 批量插入
	 */
	public static <R> int ignoreInsertMultiple(ToIntFunction<MultiRowInsertStatementProvider<R>> mapper,
			Collection<R> records, SqlTable table, UnaryOperator<MultiRowIgnoreInsertDSL<R>> completer) {
		return mapper.applyAsInt(ignoreInsertMultiple(records, table, completer));
	}

	/**
	 * mysql的insert ignore into语句, 使用时先确认数据库是否支持该语法, 批量插入
	 */
	public static <R> MultiRowInsertStatementProvider<R> ignoreInsertMultiple(Collection<R> records, SqlTable table,
			UnaryOperator<MultiRowIgnoreInsertDSL<R>> completer) {
		return completer.apply(MultiRowIgnoreInsertDSL.insert(records).into(table)).build()
				.render(RenderingStrategies.MYBATIS3);
	}

	public static SelectStatementProvider groupBySelect(BasicColumn[] selectList, SqlTable table,
			BasicColumn[] groupByColumns, SortSpecification[] orderByColumns, SelectDSLCompleter completer) {
		QueryExpressionDSL<SelectModel> start = SqlBuilder.select(selectList).from(table);
		start.groupBy(groupByColumns);
		if (orderByColumns != null && orderByColumns.length != 0) {
			start.orderBy(orderByColumns);
		}
		return MyBatis3Utils.select(start, completer);
	}

}
