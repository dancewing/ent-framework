/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.db.api.exception.enums;

import io.entframework.kernel.db.api.constants.DbConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 数据库相关操作的异常枚举
 *
 * @date 2020/10/16 10:53
 */
@Getter
public enum DaoExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据库操作未知异常
     */
    DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01", "数据库操作未知异常"),
    GET_RECORD_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "02",
            "数据库中未找到记录: {}"),
    UPDATE_RECORD_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "03",
            "更新没有找到匹配的记录: {}"),
    INSERT_RECORD_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "04", "插入记录异常"),
    WRONG_ID_TYPE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "05", "参数类型错误"),
    UPDATE_ROW_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "06", "更新异常: {}"),;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DaoExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
