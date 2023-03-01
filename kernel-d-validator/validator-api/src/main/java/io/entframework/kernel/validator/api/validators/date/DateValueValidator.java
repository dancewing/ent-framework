/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.validator.api.validators.date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 日期校验格式，通过format的参数来校验格式
 *
 * @date 2020/11/18 21:30
 */
public class DateValueValidator implements ConstraintValidator<DateValue, Object> {

    private Boolean required;

    private String format;

    @Override
    public void initialize(DateValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String dateValue = String.valueOf(value);

        if (CharSequenceUtil.isEmpty(dateValue)) {
            // 校验是不是必填
            if (required) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            try {
                // 校验日期格式
                DateUtil.parse(dateValue, format);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }

    }

}
