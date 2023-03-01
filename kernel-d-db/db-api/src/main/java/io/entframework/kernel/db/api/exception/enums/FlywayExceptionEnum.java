/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.exception.enums;

import io.entframework.kernel.db.api.constants.DbConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * Flyway相关异常枚举
 *
 * @date 2021/1/18 22:59
 */
@Getter
public enum FlywayExceptionEnum implements AbstractExceptionEnum {

    /**
     * 获取不到application.yml中的数据库配置
     */
    DB_CONFIG_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01",
            "获取不到application.yml中的数据库配置，无法为flyway创建数据库链接，请检查spring.datasource配置"),

    /**
     * flyway执行迁移异常
     */
    FLYWAY_MIGRATE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "02",
            "脚本错误，flyway执行迁移异常，具体原因：{}"),

    PLUGIN_FLYWAY_CONFIG_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "03",
            "插件的Flyway配置错误，具体原因：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlywayExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
