/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.sms.modular.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.sms.modular.enums.SmsSendSourceEnum;
import io.entframework.kernel.sms.modular.enums.SmsTypeEnum;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

/**
 * 发送短信的参数
 *
 * @date 2020/10/26 22:16
 */
@Data
public class SysSmsSendParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号码为空")
    @ChineseDescription("手机号")
    private String phone;

    /**
     * 模板号
     */
    @NotBlank(message = "模板号为空")
    @ChineseDescription("模板号")
    private String templateCode;

    /**
     * 缓存 key
     */
    @ChineseDescription("缓存key")
    private String verKey;

    /**
     * 图形验证码
     */
    @ChineseDescription("图形验证码")
    private String verCode;

    /**
     * 模板中的参数
     */
    @ChineseDescription("模板中的参数")
    private Map<String, Object> params;

    /**
     * 发送源
     */
    @ChineseDescription("发送源")
    private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

    /**
     * 消息类型，1验证码，2消息，默认不传为验证码
     */
    @ChineseDescription("消息类型：1-验证码，2-消息，默认验证码")
    private SmsTypeEnum smsTypeEnum = SmsTypeEnum.SMS;

}
