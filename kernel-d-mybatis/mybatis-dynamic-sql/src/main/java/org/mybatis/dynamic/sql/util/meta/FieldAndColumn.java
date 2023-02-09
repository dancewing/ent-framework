/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.util.meta;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.annotation.ManyToOne;
import org.mybatis.dynamic.sql.annotation.Version;

import java.lang.reflect.Field;

public record FieldAndColumn(SqlColumn<Object> column, Field field) {
    public boolean isManyToOne() {
        return field.isAnnotationPresent(ManyToOne.class);
    }

    public Class<?> fieldType() {
        return field.getType();
    }

    public String fieldName() {
        return field.getName();
    }

    public String columnName() {
        return column.name();
    }

    public boolean isVersionField() {
        return this.field.isAnnotationPresent(Version.class);
    }
}
