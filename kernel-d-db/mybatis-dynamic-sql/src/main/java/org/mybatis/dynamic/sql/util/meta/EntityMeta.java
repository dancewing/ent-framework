/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.util.meta;


import io.entframework.kernel.rule.util.ReflectionKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.dynamic.sql.AnnotatedTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.annotation.*;
import org.mybatis.dynamic.sql.relation.JoinDetail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityMeta {
    private final Class<?> cls;
    private final SqlTable table;
    private final List<FieldMeta> columns;
    private List<Field> fields;
    private List<Field> relations;
    private FieldMeta primaryKey;

    private EntityMeta(Class<?> cls) {
        this.cls = cls;
        Table tableAnno = cls.getAnnotation(Table.class);
        this.table = new AnnotatedTable(tableAnno.value(), cls);
        this.columns = new ArrayList<>();
        addColumn();
    }

    public static EntityMeta of(Class<?> cls) {
        return new EntityMeta(cls);
    }

    private void addColumn() {
        fields = ReflectionKit.getFieldList(cls);
        relations = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnno = field.getAnnotation(Column.class);
                SqlColumn<Object> column = SqlColumn.of(columnAnno.name(), this.table, columnAnno.jdbcType());
                if (columnAnno.typeHandler() != TypeHandler.class) {
                    column = column.withTypeHandler(columnAnno.typeHandler().getName());
                }
                FieldMeta wrapper = new FieldMeta(column, field);
                columns.add(wrapper);
                if (field.isAnnotationPresent(Id.class)) {
                    this.primaryKey = wrapper;
                }
            }
            if (field.isAnnotationPresent(JoinColumn.class)) {
                relations.add(field);
            }
        }
    }

    public SqlTable getTable() {
        return this.table;
    }

    public List<FieldMeta> getColumns() {
        return columns;
    }

    public List<Field> getRelations() {
        return relations;
    }

    public List<FieldMeta> getColumnsWithoutPrimaryKey() {
        return this.columns.stream().filter(c -> !StringUtils.equals(c.getField().getName(), this.primaryKey.getField().getName())).collect(Collectors.toList());
    }

    public FieldMeta getPrimaryKey() {
        return primaryKey;
    }

    public BasicColumn[] getSelectColumns() {
        int size = this.columns.size();
        return this.columns.stream().map(FieldMeta::getColumn).toList().toArray(new BasicColumn[size]);
    }

    public boolean hasManyToOne() {
        return this.fields.stream().anyMatch(field -> field.isAnnotationPresent(ManyToOne.class));
    }

    public boolean hasRelation() {
        return this.relations.stream().anyMatch(this::isRelation);
    }

    public boolean hasVersion() {
        return this.fields.stream().anyMatch(this::isVersion);
    }

    private boolean isRelation(Field field) {
        return field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToMany.class);
    }

    private boolean isManyToOne(Field field) {
        return field.isAnnotationPresent(ManyToOne.class);
    }

    private boolean isVersion(Field field) {
        return field.isAnnotationPresent(Version.class);
    }

    public List<JoinDetail> getAllJoinDetails() {
        List<JoinDetail> joinDetails = new ArrayList<>();
        for (Field field : relations) {
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            EntityMeta target = Entities.getInstance(joinColumn.target());
            joinDetails.add(JoinDetail.of(findField(joinColumn.left()), target.getTable(), target.findField(joinColumn.right())));
        }
        return joinDetails;
    }

    public List<JoinDetail> getManyToOneJoinDetails() {
        List<JoinDetail> joinDetails = new ArrayList<>();
        relations.stream().filter(this::isManyToOne).forEach(field -> {
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            EntityMeta target = Entities.getInstance(joinColumn.target());
            joinDetails.add(JoinDetail.of(findField(joinColumn.left()), target.getTable(), target.findField(joinColumn.right())));
        });
        return joinDetails;
    }

    public BasicColumn[] getAllJoinSelectColumns() {
        return this.getAllJoinSelectColumns(false);
    }

    public BasicColumn[] getManyToOneSelectColumns() {
        return this.getAllJoinSelectColumns(true);
    }

    public BasicColumn[] getAllJoinSelectColumns(boolean onlyManyToOne) {
        List<BasicColumn> selectColumns = new ArrayList<>();
        for (FieldMeta wrapper : this.columns) {
            selectColumns.add(wrapper.getColumn());
        }
        List<Field> allRelations = new ArrayList<>();
        if (onlyManyToOne) {
            allRelations.addAll(relations.stream().filter(this::isManyToOne).toList());
        } else {
            allRelations.addAll(relations);
        }
        for (Field field : allRelations) {
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            EntityMeta target = Entities.getInstance(joinColumn.target());
            List<FieldMeta> targetColumns = target.getColumns();
            String tableName = target.getTable().tableNameAtRuntime();
            for (FieldMeta wrapper : targetColumns) {
                selectColumns.add(wrapper.getColumn().as(tableName + "_" + wrapper.getColumn().name()));
            }
        }
        return selectColumns.toArray(new BasicColumn[0]);
    }


    public SqlColumn<Object> findField(String fieldName) {
        SqlColumn<Object> answer = null;
        for (FieldMeta wrapper : this.columns) {
            if (StringUtils.equals(fieldName, wrapper.getField().getName())) {
                answer = wrapper.getColumn();
                break;
            }
        }
        return answer;
    }

    public Optional<FieldMeta> findVersionColumn() {
        return this.columns.stream().filter(wrapper -> wrapper.getField().isAnnotationPresent(Version.class)).findFirst();
    }

    public Class<?> getEntityClass() {
        return this.cls;
    }
}
