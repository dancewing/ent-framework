/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.util;

import io.entframework.kernel.rule.util.ReflectionKit;

public class MapperProxyUtils {
    public static Class<?> getProxyMapper(Object obj) {
        Object mapperProxy = ReflectionKit.getFieldValue(obj, "h");
        return (Class<?>) ReflectionKit.getFieldValue(mapperProxy, "mapperInterface");
    }
}
