/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.api.exception.enums;

import io.entframework.kernel.email.api.constants.MailConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 邮件相关的异常
 *
 * @date 2020/10/23 17:36
 */
@Getter
public enum EmailExceptionEnum implements AbstractExceptionEnum {

    /**
     * 邮件发送异常，请求参数存在空值
     */
    EMAIL_PARAM_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MailConstants.MAIL_EXCEPTION_STEP_CODE + "01",
            "邮件发送失败，请检查参数配置，{}参数可能为空"),

    /**
     * 阿里云邮件发送异常
     */
    ALIYUN_MAIL_SEND_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + MailConstants.MAIL_EXCEPTION_STEP_CODE + "02",
            "阿里云邮件发送异常，errorCode：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    EmailExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
