/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.util;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.mds.extend.insert.InsertIgnoreDSL;
import io.entframework.kernel.db.mds.extend.insert.MultiRowIgnoreInsertDSL;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import org.mybatis.dynamic.sql.*;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.relation.JoinDetail;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.DefaultSelectStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.meta.Entities;
import org.mybatis.dynamic.sql.util.meta.EntityMeta;
import org.mybatis.dynamic.sql.util.meta.FieldAndColumn;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;

/**
 * author: jeff_qian
 */
public class MyBatis3CustomUtils {

    /**
     * 构造left join查询语句并获得单条查询结果
     */
    public static <R> R leftJoinSelectOne(Function<SelectStatementProvider, R> mapper, BasicColumn[] joinSelectList,
                                          Class<?> leftTable, List<JoinDetail> joinDetails, QueryExpressionDSL<SelectModel> queryExpressionDSL) {
        return mapper.apply(leftJoinSelect(joinSelectList, leftTable, joinDetails, queryExpressionDSL));
    }

    public static <R> List<R> leftJoinSelectList(Function<SelectStatementProvider, List<R>> mapper, BasicColumn[] joinSelectList,
                                                 Class<?> leftTable, List<JoinDetail> joinDetails, QueryExpressionDSL<SelectModel> queryExpressionDSL) {
        return mapper.apply(leftJoinSelect(joinSelectList, leftTable, joinDetails, queryExpressionDSL));
    }

    /**
     * 构造group by查询语句并获得查询结果
     */
    public static <R> List<R> groupBySelectList(Function<SelectStatementProvider, List<R>> mapper, BasicColumn[] selectList,
                                                SqlTable table, BasicColumn[] groupByColumns, SortSpecification[] orderByColumns,
                                                SelectDSLCompleter completer) {
        return mapper.apply(groupBySelect(selectList, table, groupByColumns, orderByColumns, completer));
    }

    /**
     * 计算select sql语句执行结果的数量. e.g. select count(*) from (select id, user from table) temp_alias_table
     */
    public static long countFromSelectQuery(ToLongFunction<SelectStatementProvider> mapper, SelectStatementProvider selectStatementProvider) {
        String sql = String.format("select count(*) from (%s) temp_alias_table", selectStatementProvider.getSelectStatement());
        DefaultSelectStatementProvider provider = DefaultSelectStatementProvider.withSelectStatement(sql).withParameters(selectStatementProvider.getParameters()).build();
        return mapper.applyAsLong(provider);
    }

    /**
     * 构造分页语句, page从1开始计数, 如果size为空或小于0则查询全部
     */
    public static QueryExpressionDSL<SelectModel> buildPagination(QueryExpressionDSL<SelectModel> completer, Integer page, Integer size) {
        if (size != null && size >= 0) {
            completer.limit(size);
            if (page != null && page - 1 >= 0) {
                int offset = (page - 1) * size;
                completer.offset(offset);
            }
        }
        return completer;
    }

    /**
     * mysql的insert ignore into语句, 使用时先确认数据库是否支持该语法
     */
    public static <R> int ignoreInsert(ToIntFunction<InsertStatementProvider<R>> mapper, R row,
                                       SqlTable table, UnaryOperator<InsertIgnoreDSL<R>> completer) {
        return mapper.applyAsInt(ignoreInsert(row, table, completer));
    }

    /**
     * mysql的insert ignore into语句, 使用时先确认数据库是否支持该语法
     */
    public static <R> InsertStatementProvider<R> ignoreInsert(R row, SqlTable table,
                                                              UnaryOperator<InsertIgnoreDSL<R>> completer) {
        return completer.apply(InsertIgnoreDSL.insert(row).into(table))
                .build()
                .render(RenderingStrategies.MYBATIS3);
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
        return completer.apply(MultiRowIgnoreInsertDSL.insert(records).into(table))
                .build()
                .render(RenderingStrategies.MYBATIS3);
    }

    public static SelectStatementProvider groupBySelect(BasicColumn[] selectList, SqlTable table, BasicColumn[] groupByColumns,
                                                        SortSpecification[] orderByColumns, SelectDSLCompleter completer) {
        QueryExpressionDSL<SelectModel> start = SqlBuilder.select(selectList).from(table);
        start.groupBy(groupByColumns);
        if (orderByColumns != null && orderByColumns.length != 0) {
            start.orderBy(orderByColumns);
        }
        return MyBatis3Utils.select(start, completer);
    }

    private static void applyJoinCondition(List<JoinDetail> joinDetails, QueryExpressionDSL<SelectModel> start) {
        joinDetails.forEach(detail -> {
            Class<?> leftJoinClass = detail.getRightJoinTable();
            EntityMeta entityMeta = Entities.getInstance(leftJoinClass);
            QueryExpressionDSL<SelectModel>.JoinSpecificationFinisher specificationFinisher = start.leftJoin(entityMeta.getTable())
                    .on(detail.getLeftTableJoinColumn(), equalTo(detail.getRightTableJoinColumn()));

            Optional<FieldAndColumn> entityMetaColumn = entityMeta.findColumn(LogicDelete.class);
            if (entityMetaColumn.isPresent()) {
                FieldAndColumn logicDeleteColumn = entityMetaColumn.get();
                specificationFinisher.and(logicDeleteColumn.column(), SqlBuilder.equalTo(StringConstant.of(YesOrNotEnum.N.getValue())));
            }
        });
    }

    /**
     *
     */
    private static SelectStatementProvider leftJoinSelect(BasicColumn[] joinSelectList, Class<?> leftTable,
                                                          List<JoinDetail> joinDetails, QueryExpressionDSL<SelectModel> queryExpressionDSL) {
        EntityMeta entityMeta = Entities.getInstance(leftTable);
        QueryExpressionDSL<SelectModel> start = SqlBuilder.select(joinSelectList).from(queryExpressionDSL, entityMeta.getTable().tableNameAtRuntime(), leftTable);
        applyJoinCondition(joinDetails, start);
        return start.build().render(RenderingStrategies.MYBATIS3);
    }
}
