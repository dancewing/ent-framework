/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.exception.enums.organization;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 数据范围异常枚举
 *
 * @date 2020/11/5 16:04
 */
@Getter
public enum DataScopeExceptionEnum implements AbstractExceptionEnum {

    /**
     * 操作失败，当前用户没有该数据的数据权限
     */
    DATA_SCOPE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "51",
            "操作失败，当前用户没有该数据的数据权限，当前数据范围是：{}"),

    /**
     * 用户id为空
     */
    USER_ID_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "52",
            "获取数据范围失败，用户id为空"),

    /**
     * 用户角色为空
     */
    ROLE_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "53",
            "获取数据范围失败，用户角色为空，userId：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DataScopeExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
