/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.validator.api.validators.flag;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.rule.enums.YesOrNotEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 校验标识，只有Y和N两种状态的标识
 *
 * @date 2020/10/31 14:53
 */
public class FlagValueValidator implements ConstraintValidator<FlagValue, Object> {

    private Boolean required;

    @Override
    public void initialize(FlagValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String flagValue = String.valueOf(value);
        // 如果是必填的
        if (required) {
            return YesOrNotEnum.Y.getValue().equals(flagValue) || YesOrNotEnum.N.getValue().equals(flagValue);
        } else {

            //如果不是必填，可以为空
            if (CharSequenceUtil.isEmpty(flagValue)) {
                return true;
            } else {
                return YesOrNotEnum.Y.getValue().equals(flagValue) || YesOrNotEnum.N.getValue().equals(flagValue);
            }
        }
    }
}
