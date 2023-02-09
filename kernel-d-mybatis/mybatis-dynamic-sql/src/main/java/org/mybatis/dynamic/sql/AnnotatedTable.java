/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql;

public class AnnotatedTable extends SqlTable {

    private final Class<?> entityClass;

    public AnnotatedTable(String tableName, Class<?> entityClass) {
        super(tableName);
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
