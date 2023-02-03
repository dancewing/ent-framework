/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.exception.enums.defaults;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 系统本身的一些运行时错误
 *
 * @date 2020/10/15 17:31
 */
@Getter
public enum DefaultSystemExceptionEnum implements AbstractExceptionEnum {
    CAN_NOT_READ_FIELD(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + RuleConstants.FIRST_LEVEL_WIDE_CODE, "不能读取字段");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DefaultSystemExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
