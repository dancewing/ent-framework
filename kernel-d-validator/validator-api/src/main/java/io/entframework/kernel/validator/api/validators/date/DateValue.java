/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.validator.api.validators.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期格式的校验，根据format参数的格式校验
 * <p>
 * format可以填写：
 * <p>
 * yyyy-MM-dd
 * <p>
 * yyyy-MM-dd HH:mm:ss
 *
 * @date 2020/11/18 21:27
 */
@Documented
@Constraint(validatedBy = DateValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValue {

    String message() default "日期格式不正确，正确格式应为yyyy-MM-dd";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 日期校验的格式，默认 yyyy-MM-dd
     */
    String format() default "yyyy-MM-dd";

    /**
     * 是否必填
     * <p>
     * 如果必填，在校验的时候本字段没值就会报错
     */
    boolean required() default true;

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        DateValue[] value();

    }
}
