/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.api.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * 发送系统消息的参数
 *
 * @date 2021/1/1 20:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageSendRequest extends BaseRequest {

    /**
     * 接收用户id字符串，多个以,分割
     */
    @NotBlank(message = "接收用户ID字符串不能为空", groups = {add.class, update.class})
    @ChineseDescription("接收用户id字符串，多个以,分割")
    private String receiveUserIds;

    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空", groups = {add.class, update.class})
    @ChineseDescription("消息标题")
    private String messageTitle;

    /**
     * 消息的内容
     */
    @ChineseDescription("消息内容")
    private String messageContent;

    /**
     * 消息类型
     */
    @ChineseDescription("消息类型")
    private String messageType;

    /**
     * 消息优先级
     */
    @ChineseDescription("消息优先级")
    private String priorityLevel;

    /**
     * 业务id
     */
    @NotNull(message = "业务id不能为空", groups = {add.class, update.class})
    @ChineseDescription("业务id")
    private Long businessId;

    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空", groups = {add.class, update.class})
    @ChineseDescription("业务类型")
    private String businessType;

    /**
     * 业务类型值
     */
    @ChineseDescription("业务类型值")
    private String businessTypeValue;

    /**
     * 消息发送时间
     */
    @ChineseDescription("消息发送时间")
    private Date messageSendTime;

}
