/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.util.meta;

import org.mybatis.dynamic.sql.annotation.Table;
import org.mybatis.dynamic.sql.exception.DynamicSqlException;
import org.mybatis.dynamic.sql.util.Assert;

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

    public static void register(Class<?> cls) {
        EntityMeta classInfo = EntityMeta.of(cls);
        if (rootClassInfoMap.containsKey(cls.getName())) {
            throw new DynamicSqlException("Entity {" + cls.getName() + "} has been registered");
        }
        else {
            rootClassInfoMap.put(cls.getName(), classInfo);
        }
    }

    public static void register(String clsName) {
        Assert.notNull(clsName, "Classname can't not be null");
        if (rootClassInfoMap.containsKey(clsName)) {
            throw new DynamicSqlException("Entity {" + clsName + "} has been registered");
        }
        Class<?> cls = null;
        try {
            cls = Class.forName(clsName);
        }
        catch (Exception ex) {
            // ignore
        }
        if (cls == null) {
            throw new DynamicSqlException("Entity {" + clsName + "} can't be determined");
        }
        EntityMeta classInfo = EntityMeta.of(cls);
        rootClassInfoMap.put(cls.getName(), classInfo);
    }

}
