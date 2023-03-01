/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.pojo.response;

import io.entframework.kernel.message.api.enums.MessageBusinessTypeEnum;
import io.entframework.kernel.message.api.enums.MessagePriorityLevelEnum;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 系统消息的查询参数
 *
 * @date 2021/1/2 21:23
 */
@Data
public class SysMessageResponse implements Serializable {

    /**
     * 消息id
     */
    @ChineseDescription("消息id")
    private Long messageId;

    /**
     * 接收用户id
     */
    @ChineseDescription("接收用户id")
    private Long receiveUserId;

    /**
     * 发送用户id
     */
    @ChineseDescription("发送用户id")
    private Long sendUserId;

    /**
     * 发送用户名
     */
    @ChineseDescription("发送用户名")
    private String sendUserName;

    /**
     * 消息标题
     */
    @ChineseDescription("消息标题")
    private String messageTitle;

    /**
     * 消息的内容
     */
    @ChineseDescription("消息内容")
    private String messageContent;

    /**
     * 消息优先级
     */
    @ChineseDescription("消息优先级")
    private String priorityLevel;

    /**
     * 消息类型
     */
    @ChineseDescription("消息类型")
    private String messageType;

    /**
     * 消息发送时间
     */
    @ChineseDescription("消息发送时间")
    private Date messageSendTime;

    /**
     * 业务id
     */
    @ChineseDescription("业务id")
    private Long businessId;

    /**
     * 业务类型
     */
    @ChineseDescription("业务类型")
    private String businessType;

    /**
     * 阅读状态：0-未读，1-已读
     */
    @ChineseDescription("阅读状态：0-未读，1-已读")
    private MessageReadFlagEnum readFlag;

    /**
     * 消息优先级
     */
    @ChineseDescription("消息优先级")
    private String priorityLevelValue;

    /**
     * 阅读状态：0-未读，1-已读
     */
    @ChineseDescription("阅读状态：0-未读，1-已读")
    private String readFlagValue;

    public String getPriorityLevelValue() {
        AtomicReference<String> value = new AtomicReference<>("");
        Optional.ofNullable(this.priorityLevel).ifPresent(val -> {
            value.set(MessagePriorityLevelEnum.getName(this.priorityLevel));
        });
        return value.get();
    }

    public String getReadFlagValue() {
        AtomicReference<String> value = new AtomicReference<>("");
        Optional.ofNullable(this.readFlag).ifPresent(val -> {
            value.set(this.readFlag.getName());
        });
        return value.get();
    }

    public String getBusinessTypeValue() {
        AtomicReference<String> value = new AtomicReference<>("");
        Optional.ofNullable(this.businessType).ifPresent(val -> {
            value.set(MessageBusinessTypeEnum.getName(this.businessType));
        });
        return value.get();
    }

}
