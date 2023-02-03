/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.exception.enums;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.security.api.constants.SecurityConstants;
import lombok.Getter;

/**
 * XSS过滤异常的枚举
 *
 * @date 2021/1/13 23:23
 */
@Getter
public enum XssFilterExceptionEnum implements AbstractExceptionEnum {

    /**
     * XSS初始化配置为空
     */
    CONFIG_IS_NULL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SecurityConstants.SECURITY_EXCEPTION_STEP_CODE + "11", "XSS初始化配置为空，请检查XSS过滤器配置是否正确！");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    XssFilterExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
