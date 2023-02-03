/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.meta;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.annotation.ManyToOne;
import org.mybatis.dynamic.sql.SqlColumn;

import java.lang.reflect.Field;

public class FieldMeta {
    private SqlColumn<Object> column;
    private Field field;

    public FieldMeta(SqlColumn<Object> column, Field field) {
        this.column = column;
        this.field = field;
    }

    public SqlColumn<Object> getColumn() {
        return column;
    }

    public FieldMeta withColumn(SqlColumn<Object> column) {
        this.column = column;
        return this;
    }

    public Field getField() {
        return field;
    }

    public FieldMeta withField(Field field) {
        this.field = field;
        return this;
    }

    public boolean isManyToOne() {
        return field.isAnnotationPresent(ManyToOne.class);
    }

    public boolean isLogicDelete() {
        return field.isAnnotationPresent(LogicDelete.class);
    }
}
