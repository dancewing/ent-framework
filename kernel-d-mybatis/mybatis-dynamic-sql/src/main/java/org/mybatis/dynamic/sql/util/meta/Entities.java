/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.util.meta;

import org.mybatis.dynamic.sql.annotation.Table;
import org.mybatis.dynamic.sql.exception.DynamicSqlException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Entities {
    private static final Map<String, EntityMeta> rootClassInfoMap = new ConcurrentHashMap<>();

    public static EntityMeta getInstance(Class<?> cls) {
        Class<?> entityClass = Objects.requireNonNull(cls);
        if (rootClassInfoMap.containsKey(entityClass.getName())) {
            return rootClassInfoMap.get(entityClass.getName());
        }
        if (entityClass.isAnnotationPresent(Table.class)) {
            EntityMeta classInfo = EntityMeta.of(cls);
            rootClassInfoMap.put(cls.getName(), classInfo);
            return classInfo;
        }
        throw new DynamicSqlException("Wrong entity class");
    }
}
