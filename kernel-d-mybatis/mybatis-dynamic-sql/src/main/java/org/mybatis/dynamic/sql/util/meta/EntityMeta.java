/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.util.meta;

import org.apache.ibatis.type.TypeHandler;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.annotation.*;
import org.mybatis.dynamic.sql.exception.DynamicSqlException;
import org.mybatis.dynamic.sql.relation.JoinDetail;
import org.mybatis.dynamic.sql.util.Assert;
import org.mybatis.dynamic.sql.util.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EntityMeta {

	private final Class<?> cls;

	private final SqlTable table;

	private final List<FieldAndColumn> columns;

	private List<Field> fields;

	private List<Field> relations;

	private FieldAndColumn primaryKey;

	private EntityMeta(Class<?> cls) {
		this.cls = Objects.requireNonNull(cls, "Entity must not be null");
		Table tableAnno = cls.getAnnotation(Table.class);
		if (tableAnno == null) {
			throw new DynamicSqlException("The entity {" + this.cls.getName() + "} must contains @Table annotation");
		}
		if (tableAnno.sqlSupport() != void.class && tableAnno.tableProperty() != null) {
			this.table = (SqlTable) ReflectUtils.getFieldValue(ReflectUtils.newInstance(tableAnno.sqlSupport()),
					tableAnno.tableProperty());
		}
		else {
			Assert.hasText(tableAnno.value(), "@Table has empty table name");
			this.table = SqlTable.of(tableAnno.value());
		}
		this.columns = new ArrayList<>();
		addColumn();
	}

	static EntityMeta of(Class<?> cls) {
		return new EntityMeta(cls);
	}

	private void addColumn() {
		fields = ReflectUtils.getFieldList(cls).stream()
				/* 过滤静态属性 */
				.filter(f -> !Modifier.isStatic(f.getModifiers()))
				/* 过滤 transient关键字修饰的属性 */
				.filter(f -> !Modifier.isTransient(f.getModifiers())).toList();

		relations = new ArrayList<>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Column.class)) {
				Column columnAnno = field.getAnnotation(Column.class);
				SqlColumn<Object> column = SqlColumn.of(columnAnno.name(), this.table, columnAnno.jdbcType());
				if (columnAnno.typeHandler() != TypeHandler.class) {
					column = column.withTypeHandler(columnAnno.typeHandler().getName());
				}
				FieldAndColumn wrapper = new FieldAndColumn(column, field);
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

	public List<FieldAndColumn> getColumns() {
		return columns;
	}

	public List<Field> getRelations() {
		return relations;
	}

	public List<FieldAndColumn> getColumnsForUpdate() {
		return this.columns.stream().filter(c -> !c.field().isAnnotationPresent(Id.class)).toList();
	}

	public List<FieldAndColumn> getColumnsForInsert() {
		return this.columns.stream().filter(c -> !c.field().isAnnotationPresent(GeneratedValue.class)).toList();
	}

	public FieldAndColumn getPrimaryKey() {
		return primaryKey;
	}

	public BasicColumn[] getSelectColumns() {
		int size = this.columns.size();
		return this.columns.stream().map(FieldAndColumn::column).toList().toArray(new BasicColumn[size]);
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
			JoinDetail.of(findField(joinColumn.left()).orElse(null), joinColumn.target(),
					target.findField(joinColumn.right()).orElse(null)).ifPresent(joinDetails::add);
		}
		return joinDetails;
	}

	public List<JoinDetail> getManyToOneJoinDetails() {
		List<JoinDetail> joinDetails = new ArrayList<>();
		relations.stream().filter(this::isManyToOne).forEach(field -> {
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			EntityMeta target = Entities.getInstance(joinColumn.target());
			JoinDetail.of(findField(joinColumn.left()).orElse(null), joinColumn.target(),
					target.findField(joinColumn.right()).orElse(null)).ifPresent(joinDetails::add);
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
		for (FieldAndColumn wrapper : this.columns) {
			selectColumns.add(wrapper.column());
		}
		List<Field> allRelations = new ArrayList<>();
		if (onlyManyToOne) {
			allRelations.addAll(relations.stream().filter(this::isManyToOne).toList());
		}
		else {
			allRelations.addAll(relations);
		}
		for (Field field : allRelations) {
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			EntityMeta target = Entities.getInstance(joinColumn.target());
			List<FieldAndColumn> targetColumns = target.getColumns();
			String tableName = target.getTable().tableNameAtRuntime();
			for (FieldAndColumn wrapper : targetColumns) {
				selectColumns.add(wrapper.column().as(tableName + "_" + wrapper.column().name()));
			}
		}
		return selectColumns.toArray(new BasicColumn[0]);
	}

	public Optional<SqlColumn<Object>> findField(String fieldName) {
		if (fieldName == null || fieldName.trim().isEmpty()) {
			return Optional.empty();
		}
		return this.columns.stream().filter(fieldAndColumn -> fieldAndColumn.fieldName().equals(fieldName))
				.map(FieldAndColumn::column).findFirst();
	}

	public Optional<FieldAndColumn> findColumn(Class<? extends Annotation> annotationClass) {
		return this.columns.stream().filter(wrapper -> wrapper.field().isAnnotationPresent(annotationClass))
				.findFirst();
	}

	public List<Field> findColumns(Class<? extends Annotation> annotationClass) {
		return this.relations.stream().filter(wrapper -> wrapper.isAnnotationPresent(annotationClass)).toList();
	}

	public Optional<FieldAndColumn> findVersionColumn() {
		return this.columns.stream().filter(wrapper -> wrapper.field().isAnnotationPresent(Version.class)).findFirst();
	}

	public Class<?> getEntityClass() {
		return this.cls;
	}

}
