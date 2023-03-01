/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.rule.annotation;

import java.lang.annotation.*;

/**
 * 加在字段上，描述字段的中文名称
 * <p>
 * 用来解决资源扫描时候，扫描的类的字段上的中文注释获取的问题
 * </p>
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChineseDescription {

    /**
     * 中文注释的值
     */
    String value() default "";

}
