/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api.exception.enums;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 监控模块异常枚举
 *
 * @date 2021/1/31 22:35
 */
@Getter
public enum MonitorExceptionEnum implements AbstractExceptionEnum {

    /**
     * prometheus配置异常
     */
    PROMETHEUS_CONFIG_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + RuleConstants.RULE_EXCEPTION_STEP_CODE + "02",
            "prometheus配置异常，具体信息为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    MonitorExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
