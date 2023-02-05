/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.meta;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.api.util.ClassUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Entities {
    private static final Map<String, EntityMeta> rootClassInfoMap = new ConcurrentHashMap<>();


    public static EntityMeta fromMapper(Class<?> cls) {
        Assert.notNull(cls, "参数不能为空");
        Class<?> actualCls = ClassUtils.getUserClass(cls);
        if (actualCls.isAnnotationPresent(Entity.class)) {
            Entity entityAnno = actualCls.getAnnotation(Entity.class);
            Class<?> entityCls = entityAnno.value();
            return getInstance(entityCls);
        }

        throw new DaoException(DaoExceptionEnum.DAO_ERROR);
    }

    public static EntityMeta getInstance(Class<?> cls) {
        Assert.notNull(cls, "参数不能为空");
        if (rootClassInfoMap.containsKey(cls.getName())) {
            return rootClassInfoMap.get(cls.getName());
        }
        if (cls.isAnnotationPresent(Table.class)) {
            EntityMeta classInfo = EntityMeta.of(cls);
            rootClassInfoMap.put(cls.getName(), classInfo);
            return classInfo;
        }
        throw new DaoException(DaoExceptionEnum.DAO_ERROR);
    }
}
