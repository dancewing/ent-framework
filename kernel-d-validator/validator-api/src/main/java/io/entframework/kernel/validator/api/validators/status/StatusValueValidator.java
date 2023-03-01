/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.validator.api.validators.status;

import io.entframework.kernel.rule.enums.StatusEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 校验状态，判断是否为 StatusEnum 中的值
 *
 * @date 2020/10/31 14:52
 */
public class StatusValueValidator implements ConstraintValidator<StatusValue, Object> {

    private Boolean required;

    @Override
    public void initialize(StatusValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 如果是必填的
        if (required && value == null) {
            return false;
        }

        // 如果不是必填，为空的话就通过
        if (!required && value == null) {
            return true;
        }
        Integer statusValue = NumberUtils.toInt(String.valueOf(value));
        // 校验值是否是枚举中的值
        StatusEnum statusEnum = StatusEnum.valueToEnum(statusValue);
        return statusEnum != null;

    }

}
