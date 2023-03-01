/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.exception.enums.defaults;

import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * 系统执行出错，业务本身逻辑问题导致的错误（一级宏观码）
 *
 * @date 2020/10/15 17:18
 */
@Getter
public enum DefaultBusinessExceptionEnum implements AbstractExceptionEnum {

    /**
     * 系统执行出错（一级宏观错误码）
     */
    SYSTEM_RUNTIME_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + RuleConstants.FIRST_LEVEL_WIDE_CODE,
            "系统执行出错，请检查系统运行状况"),
    ENUM_CONVERT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + "0002", "枚举反序列化失败"),
    WRONG_ARGS_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + "0003", "参数异常"),;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DefaultBusinessExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
