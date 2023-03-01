/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.util.meta;

import org.mybatis.dynamic.sql.SqlColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public record FieldAndColumn(SqlColumn<Object> column, Field field) {
    public Class<?> fieldType() {
        return field.getType();
    }

    public String fieldName() {
        return field.getName();
    }

    public String columnName() {
        return column.name();
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.field.isAnnotationPresent(annotationClass);
    }
}
