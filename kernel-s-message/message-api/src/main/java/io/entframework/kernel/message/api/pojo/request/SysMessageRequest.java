/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.api.pojo.request;

import java.util.Date;
import java.util.List;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import io.entframework.kernel.message.api.enums.MessageBusinessTypeEnum;
import io.entframework.kernel.message.api.enums.MessagePriorityLevelEnum;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.rule.annotation.ChineseDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统消息的查询参数
 *
 * @date 2021/1/1 20:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMessageRequest extends BaseRequest {

	/**
	 * 消息id
	 */
	@NotNull(message = "messageId不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
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
	 * 消息类型
	 */
	@ChineseDescription("消息类型")
	private String messageType;

	/**
	 * 消息优先级
	 */
	@ChineseDescription("消息优先级")
	private MessagePriorityLevelEnum priorityLevel;

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
	private MessageBusinessTypeEnum businessType;

	/**
	 * 阅读状态：0-未读，1-已读
	 */
	@NotNull(message = "阅读状态不能为空", groups = { updateStatus.class })
	@ChineseDescription("阅读状态")
	private MessageReadFlagEnum readFlag;

	/**
	 * 消息id集合
	 */
	@NotEmpty(message = "消息id集合不能为空", groups = { updateReadFlag.class })
	@ChineseDescription("消息id集合")
	private List<Long> messageIdList;

	/**
	 * 参数校验分组：修改阅读状态
	 */
	public @interface updateReadFlag {

	}

}
