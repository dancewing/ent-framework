/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.exception.enums.menu;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import io.entframework.kernel.system.api.constants.SystemConstants;

/**
 * 菜单按钮异常枚举
 *
 * @date 2021/1/9 11:32
 */
public enum SysMenuButtonExceptionEnum implements AbstractExceptionEnum {

    /**
     * 菜单按钮不存在
     */
    MENU_BUTTON_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "31", "菜单按钮不存在");

    private final String errorCode;

    private final String userTip;

    SysMenuButtonExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getUserTip() {
        return userTip;
    }
}
