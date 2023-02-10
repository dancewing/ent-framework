/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.message.db.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.repository.GeneralRepository;
import io.entframework.kernel.message.api.MessageApi;
import io.entframework.kernel.message.api.constants.MessageConstants;
import io.entframework.kernel.message.api.enums.MessageReadFlagEnum;
import io.entframework.kernel.message.api.exception.MessageException;
import io.entframework.kernel.message.api.exception.enums.MessageExceptionEnum;
import io.entframework.kernel.message.api.pojo.request.MessageSendRequest;
import io.entframework.kernel.message.api.pojo.request.SysMessageRequest;
import io.entframework.kernel.message.api.pojo.response.SysMessageResponse;
import io.entframework.kernel.message.db.entity.SysMessage;
import io.entframework.kernel.message.db.entity.SysMessageDynamicSqlSupport;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.socket.api.SocketClientOperatorApi;
import io.entframework.kernel.socket.api.SocketOperatorApi;
import io.entframework.kernel.socket.api.enums.ServerMessageTypeEnum;
import io.entframework.kernel.socket.api.exception.SocketException;
import io.entframework.kernel.system.api.UserClientServiceApi;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统消息，数据库实现
 *
 * @date 2021/1/2 22:00
 */
@Slf4j
@Service
public class MessageDbServiceImpl implements MessageApi {

	private SocketClientOperatorApi socketClientOperatorApi;

	@Resource
	@Qualifier("mvcConversionService")
	@Lazy
	private ConversionService conversionService;

	@Autowired(required = false)
	public void setSocketClientOperatorApi(SocketOperatorApi socketClientOperatorApi) {
		this.socketClientOperatorApi = socketClientOperatorApi;
	}

	@Resource
	private UserClientServiceApi userServiceApi;

	@Resource
	private SysMessageService sysMessageService;

	@Resource
	private GeneralRepository generalRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendMessage(MessageSendRequest messageSendRequest) {

		String receiveUserIds = messageSendRequest.getReceiveUserIds();
		LoginUser loginUser = LoginContext.me().getLoginUser();

		List<SysMessage> sendMsgList = new ArrayList<>();
		List<Long> userIds;

		// 发送所有人判断
		if (MessageConstants.RECEIVE_ALL_USER_FLAG.equals(receiveUserIds)) {
			// 查询所有用户
			userIds = userServiceApi.queryAllUserIdList(new SysUserRequest());
		} else {
			String[] userIdArr = receiveUserIds.split(",");
			userIds = Convert.toList(Long.class, userIdArr);
		}

		// 无人可发，不发送
		if (userIds == null || userIds.isEmpty()) {
			throw new MessageException(MessageExceptionEnum.ERROR_RECEIVE_USER_IDS);
		}

		Set<Long> userIdSet = new HashSet<>(userIds);
		for (Long userId : userIdSet) {
			// 判断用户是否存在
			Boolean userExist = userServiceApi.userExist(userId);
			if (userExist != null && userExist) {
				SysMessage sysMessage = new SysMessage();
				BeanUtil.copyProperties(messageSendRequest, sysMessage);
				// 初始化默认值
				sysMessage.setReadFlag(MessageReadFlagEnum.UNREAD);
				sysMessage.setSendUserId(loginUser.getUserId());
				sysMessage.setReceiveUserId(userId);
				sendMsgList.add(sysMessage);
			}
		}
		sysMessageService.batchCreateEntity(sendMsgList);

		if (this.socketClientOperatorApi != null) {
			// 给用户发送通知
			for (SysMessage item : sendMsgList) {
				try {
					this.socketClientOperatorApi.sendMsgOfUserSession(
							ServerMessageTypeEnum.SYS_NOTICE_MSG_TYPE.getCode(), item.getReceiveUserId().toString(),
							item);
				} catch (SocketException socketException) {
					// 该用户不在线
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateReadFlag(SysMessageRequest sysMessageRequest) {
		Long messageId = sysMessageRequest.getMessageId();
		SysMessage sysMessage = this.generalRepository.get(SysMessage.class, messageId);
		if (sysMessage != null) {
			sysMessage.setReadFlag(sysMessageRequest.getReadFlag());
			sysMessageService.update(sysMessage);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void allMessageReadFlag() {
		// 获取当前登录人
		LoginUser loginUser = LoginContext.me().getLoginUser();
		Long userId = loginUser.getUserId();
		SysMessageRequest request = new SysMessageRequest();
		request.setReadFlag(MessageReadFlagEnum.UNREAD);
		request.setReceiveUserId(userId);
		sysMessageService.update(request);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchReadFlagByMessageIds(String messageIds, MessageReadFlagEnum flagEnum) {
		List<Long> ids = Arrays.stream(StringUtils.split(messageIds, ",")).map(NumberUtils::toLong)
				.toList();
		UpdateDSLCompleter completer = c -> c.set(SysMessageDynamicSqlSupport.readFlag).equalTo(flagEnum)
				.where(SysMessageDynamicSqlSupport.messageId, SqlBuilder.isIn(ids));
		this.generalRepository.update(SysMessage.class, completer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMessageId(Long messageId) {
		SysMessageRequest request = new SysMessageRequest();
		request.setMessageId(messageId);
		sysMessageService.del(request);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchDeleteByMessageIds(String messageIds) {
		List<Long> ids = Arrays.stream(StringUtils.split(messageIds, ",")).map(NumberUtils::toLong)
				.toList();
		UpdateDSLCompleter completer = c -> c.set(SysMessageDynamicSqlSupport.delFlag).equalTo(YesOrNotEnum.Y)
				.where(SysMessageDynamicSqlSupport.messageId, SqlBuilder.isIn(ids));
		this.generalRepository.update(SysMessage.class, completer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysMessageResponse messageDetail(SysMessageRequest sysMessageRequest) {
		SysMessage sysMessage = this.generalRepository.get(SysMessage.class, sysMessageRequest.getMessageId());
		// 判断消息为未读状态更新为已读
		if (sysMessage != null) {
			sysMessage.setReadFlag(MessageReadFlagEnum.READ);
			sysMessageService.update(sysMessage);
			return this.conversionService.convert(sysMessage, SysMessageResponse.class);
		}
		return null;
	}

	@Override
	public PageResult<SysMessageResponse> queryPage(SysMessageRequest sysMessageRequest) {
		PageResult<SysMessageResponse> pageResult = sysMessageService.findPage(sysMessageRequest);
		PageResult<SysMessageResponse> result = new PageResult<>();
		List<SysMessageResponse> messageList = pageResult.getItems();
		List<SysMessageResponse> resultList = messageList.stream().map(msg -> {
			SysMessageResponse response = new SysMessageResponse();
			BeanUtil.copyProperties(msg, response);
			return response;
		}).toList();
		BeanUtil.copyProperties(pageResult, result);
		result.setItems(resultList);
		return result;
	}

	@Override
	public List<SysMessageResponse> queryList(SysMessageRequest sysMessageRequest) {
		List<SysMessageResponse> messageList = sysMessageService.findList(sysMessageRequest);
		return messageList.stream().map(msg -> {
			SysMessageResponse response = new SysMessageResponse();
			BeanUtil.copyProperties(msg, response);
			return response;
		}).toList();
	}

	@Override
	public PageResult<SysMessageResponse> queryPageCurrentUser(SysMessageRequest sysMessageRequest) {
		if (ObjectUtil.isEmpty(sysMessageRequest)) {
			sysMessageRequest = new SysMessageRequest();
		}
		// 获取当前登录人
		LoginUser loginUser = LoginContext.me().getLoginUser();
		sysMessageRequest.setReceiveUserId(loginUser.getUserId());
		return this.queryPage(sysMessageRequest);
	}

	@Override
	public List<SysMessageResponse> queryListCurrentUser(SysMessageRequest sysMessageRequest) {
		if (ObjectUtil.isEmpty(sysMessageRequest)) {
			sysMessageRequest = new SysMessageRequest();
		}
		// 获取当前登录人
		LoginUser loginUser = LoginContext.me().getLoginUser();
		sysMessageRequest.setReceiveUserId(loginUser.getUserId());
		return this.queryList(sysMessageRequest);
	}

	@Override
	public long queryCount(SysMessageRequest sysMessageRequest) {
		return sysMessageService.findCount(sysMessageRequest);
	}

	@Override
	public long queryCountCurrentUser(SysMessageRequest sysMessageRequest) {
		if (ObjectUtil.isEmpty(sysMessageRequest)) {
			sysMessageRequest = new SysMessageRequest();
		}
		// 获取当前登录人
		LoginUser loginUser = LoginContext.me().getLoginUser();
		sysMessageRequest.setReceiveUserId(loginUser.getUserId());
		return this.queryCount(sysMessageRequest);
	}

}
