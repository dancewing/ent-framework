/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.mapper;

import io.entframework.kernel.db.mds.extend.update.EntityUpdateMapper;
import io.entframework.kernel.db.mds.meta.Entities;
import io.entframework.kernel.db.mds.meta.EntityMeta;
import io.entframework.kernel.db.mds.meta.FieldMeta;
import io.entframework.kernel.db.mds.util.MapperProxyUtils;
import io.entframework.kernel.db.mds.util.MyBatis3CustomUtils;
import io.entframework.kernel.rule.util.ReflectionKit;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

public interface BaseMapper<T> extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<T>, CommonUpdateMapper, EntityUpdateMapper<T> {
    Logger log = LoggerFactory.getLogger("mapper.mds.db.kernel.io.entframework.BaseMapper");

    default int insert(T row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.insert(this::insert, row, entities.getTable(), c -> {
                    List<FieldMeta> columns = entities.getColumns();
                    columns.forEach(wrapper -> c.map(wrapper.getColumn()).toProperty(wrapper.getField().getName()));
                    return c;
                }
        );
    }

    default int insertMultiple(Collection<T> records) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, entities.getTable(), c -> {
                    List<FieldMeta> columns = entities.getColumns();
                    columns.forEach(wrapper -> c.map(wrapper.getColumn()).toProperty(wrapper.getField().getName()));
                    return c;
                }
        );
    }

    default int insertSelective(T row) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return MyBatis3Utils.insert(this::insert, row, entities.getTable(), c -> {
                    List<FieldMeta> columns = entities.getColumns();
                    columns.forEach(wrapper -> {
                        Object value = ReflectionKit.getFieldValue(row, wrapper.getField().getName());
                        c.map(wrapper.getColumn()).toPropertyWhenPresent(wrapper.getField().getName(), () -> value);
                    });
                    return c;
                }
        );
    }

    default int delete(DeleteDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.deleteFrom(this::delete, entities.getTable(), completer);
    }

    default int deleteByPrimaryKey(Object id) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        SqlColumn<Object> pk = entities.getPrimaryKey().getColumn();
        return delete(c ->
                c.where(pk, isEqualTo(id))
        );
    }


    default int update(UpdateDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.update(this::update, entities.getTable(), completer);
    }

    default int updateByPrimaryKey(T row) {
        return this.updateByPrimaryKey(row, false);
    }

    default int updateByPrimaryKey(T row, boolean selective) {
        EntityMeta entities = Entities.getInstance(row.getClass());
        return update(c -> {
                    List<FieldMeta> columns = entities.getColumnsWithoutPrimaryKey();
                    columns.forEach(column -> {
                        Object value = ReflectionKit.getFieldValue(row, column.getField().getName());
                        if (selective) {
                            c.set(column.getColumn()).equalToWhenPresent(value);
                        } else {
                            c.set(column.getColumn()).equalTo(value);
                        }
                    });
                    FieldMeta primaryKey = entities.getPrimaryKey();
                    SqlColumn<Object> pkColumn = primaryKey.getColumn();
                    Optional<FieldMeta> versionColumn = entities.findVersionColumn();
                    try {
                        Object pkValue = ReflectionKit.getFieldValue(row, primaryKey.getField().getName());
                        if (versionColumn.isPresent()) {
                            FieldMeta versionMeta = versionColumn.get();
                            Object versionValue = ReflectionKit.getFieldValue(row, versionMeta.getField().getName());
                            c.where(pkColumn, isEqualTo(pkValue)).and(versionMeta.getColumn(), isEqualTo(versionValue));
                        } else {
                            c.where(pkColumn, isEqualTo(pkValue));
                        }
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                    return c;
                }
        );
    }

    default int update(T row) {
        EntityMeta entityMeta = Entities.getInstance(row.getClass());
        return MyBatis3CustomUtils.update(this::updateRow, row, entityMeta.getTable(), c -> {
            List<FieldMeta> columns = entityMeta.getColumns();
            columns.forEach(wrapper -> c.map(wrapper.getColumn()).toProperty(wrapper.getField().getName()));
            return c;
        });
    }

    default long count(CountDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.countFrom(this::count, entities.getTable(), completer);
    }

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<T> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    Optional<T> selectOne(SelectStatementProvider selectStatement);


    default Optional<T> selectOne(SelectDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.selectOne(this::selectOne, entities.getSelectColumns(), entities.getTable(), completer);
    }

    default List<T> select(SelectDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.selectList(this::selectMany, entities.getSelectColumns(), entities.getTable(), completer);
    }

    default List<T> selectDistinct(SelectDSLCompleter completer) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        return MyBatis3Utils.selectDistinct(this::selectMany, entities.getSelectColumns(), entities.getTable(), completer);
    }

    default Optional<T> selectByPrimaryKey(Object id) {
        EntityMeta entities = Entities.fromMapper(MapperProxyUtils.getProxyMapper(this));
        SqlColumn<Object> pk = entities.getPrimaryKey().getColumn();
        return selectOne(c ->
                c.where(pk, isEqualTo(id))
        );
    }

}
