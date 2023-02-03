/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.groovy.api.exception.enums;

import io.entframework.kernel.groovy.api.constants.GroovyConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * groovy脚本执行异常的枚举
 *
 * @date 2021/1/22 16:36
 */
@Getter
public enum GroovyExceptionEnum implements AbstractExceptionEnum {

    /**
     * groovy脚本执行异常的枚举
     */
    GROOVY_EXE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + GroovyConstants.GROOVY_EXCEPTION_STEP_CODE + "01", "执行groovy类中方法出错");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    GroovyExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
