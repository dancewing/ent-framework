/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.dynamic.sql.annotation;

import org.apache.ibatis.type.TypeHandler;

import java.lang.annotation.*;
import java.sql.JDBCType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Documented
public @interface Column {

	String name() default "";

	JDBCType jdbcType();

	boolean descending() default false;

	Class<? extends TypeHandler> typeHandler() default TypeHandler.class;

}
