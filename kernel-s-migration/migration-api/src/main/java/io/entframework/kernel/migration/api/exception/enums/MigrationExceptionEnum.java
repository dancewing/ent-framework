/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.migration.api.exception.enums;

import io.entframework.kernel.migration.api.constants.MigrationConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * Migration模块相关异常枚举
 *
 * @date 2021/7/6 15:09
 */
@Getter
public enum MigrationExceptionEnum implements AbstractExceptionEnum {

    /**
     * Migration操作异常
     */
    MIGRATION_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + MigrationConstants.MIGRATION_EXCEPTION_STEP_CODE + "01", "操作异常，具体信息为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    MigrationExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
