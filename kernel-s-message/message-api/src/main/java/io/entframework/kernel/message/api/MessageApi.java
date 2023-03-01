/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api;

import java.util.List;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.message.api.pojo.request.MessageSendRequest;
import io.entframework.kernel.message.api.pojo.request.SysMessageRequest;
import io.entframework.kernel.message.api.pojo.response.SysMessageResponse;

/**
 * 系统消息相关接口
 * <p>
 * 接口可以有多种实现，目前只实现数据库存储方式
 *
 * @date 2021/1/2 21:21
 */
public interface MessageApi {

	/**
	 * 发送系统消息
	 * @param messageSendRequest 系统消息参数
	 * @date 2021/1/2 21:21
	 */
	void sendMessage(MessageSendRequest messageSendRequest);

	/**
	 * 更新阅读状态
	 * @param sysMessageRequest 系统消息参数
	 * @date 2021/1/2 22:15
	 */
	void updateReadFlag(SysMessageRequest sysMessageRequest);

	/**
	 * 全部更新阅读状态
	 *
	 * @date 2021/1/2 22:15
	 */
	void allMessageReadFlag();

	/**
	 * 批量更新阅读状态
	 * @param messageIds 消息id字符串，多个用逗号分隔
	 * @date 2021/1/4 21:21
	 */
	void batchReadFlagByMessageIds(String messageIds, MessageReadFlagEnum flagEnum);

	/**
	 * 删除系统消息
	 * @param messageId 消息id
	 * @date 2021/1/2 21:21
	 */
	void deleteByMessageId(Long messageId);

	/**
	 * 批量删除系统消息
	 * @param messageIds 消息id字符串，多个用逗号分隔
	 * @date 2021/1/2 21:21
	 */
	void batchDeleteByMessageIds(String messageIds);

	/**
	 * 查看系统消息
	 * @param sysMessageRequest 查看参数
	 * @return 系统消息
	 * @date 2021/1/2 21:21
	 */
	SysMessageResponse messageDetail(SysMessageRequest sysMessageRequest);

	/**
	 * 查询分页系统消息
	 * @param sysMessageRequest 查询参数
	 * @return 查询分页结果
	 * @date 2021/1/2 21:21
	 */
	PageResult<SysMessageResponse> queryPage(SysMessageRequest sysMessageRequest);

	/**
	 * 查询系统消息
	 * @param sysMessageRequest 查询参数
	 * @return 系统消息列表
	 * @date 2021/1/2 21:21
	 */
	List<SysMessageResponse> queryList(SysMessageRequest sysMessageRequest);

	/**
	 * 查询分页系统消息 当前登录用户
	 * @param sysMessageRequest 查询参数
	 * @return 查询分页结果
	 * @date 2021/1/2 21:21
	 */
	PageResult<SysMessageResponse> queryPageCurrentUser(SysMessageRequest sysMessageRequest);

	/**
	 * 查询系统消息 当前登录用户
	 * @param sysMessageRequest 查询参数
	 * @return 系统消息列表
	 * @date 2021/1/2 21:21
	 */
	List<SysMessageResponse> queryListCurrentUser(SysMessageRequest sysMessageRequest);

	/**
	 * 查询系统消息数量
	 * @param sysMessageRequest 查询参数
	 * @return 系统消息数量
	 * @date 2021/1/11 21:21
	 */
	long queryCount(SysMessageRequest sysMessageRequest);

	/**
	 * 查询系统消息数量，当前登录用户
	 * @param sysMessageRequest 查询参数
	 * @return 系统消息数量
	 * @date 2021/1/11 21:21
	 */
	long queryCountCurrentUser(SysMessageRequest sysMessageRequest);

}
