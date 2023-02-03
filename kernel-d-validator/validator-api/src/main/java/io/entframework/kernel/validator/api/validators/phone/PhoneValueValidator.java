/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.validator.api.validators.phone;

import cn.hutool.core.text.CharSequenceUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 校验手机号是否为11位
 *
 * @date 2020/10/31 14:53
 */
public class PhoneValueValidator implements ConstraintValidator<PhoneValue, Object> {

    private Boolean required;

    @Override
    public void initialize(PhoneValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String phoneValue = String.valueOf(value);

        if (CharSequenceUtil.isEmpty(phoneValue)) {
            if (required) {
                return false;
            } else {
                return true;
            }
        } else {
            return phoneValue.length() == 11;
        }
    }
}
