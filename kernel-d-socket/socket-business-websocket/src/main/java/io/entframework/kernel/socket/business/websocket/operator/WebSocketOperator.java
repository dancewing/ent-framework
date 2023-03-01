/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.socket.business.websocket.operator;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.socket.api.SocketOperatorApi;
import io.entframework.kernel.socket.api.exception.SocketException;
import io.entframework.kernel.socket.api.exception.enums.SocketExceptionEnum;
import io.entframework.kernel.socket.api.message.SocketMsgCallbackInterface;
import io.entframework.kernel.socket.api.session.pojo.SocketSession;
import io.entframework.kernel.socket.business.websocket.message.SocketMessageCenter;
import io.entframework.kernel.socket.business.websocket.operator.channel.KernelSocketOperator;
import io.entframework.kernel.socket.business.websocket.pojo.WebSocketMessageDTO;
import io.entframework.kernel.socket.business.websocket.session.SessionCenter;

import java.util.Collection;
import java.util.List;

/**
 * WebSocket操作实现类
 * <p>
 * 如果是Spring boot项目，通过注入SocketOperatorApi接口操作socket，需将本来交给Spring管理
 *
 * @date 2021/6/2 上午10:41
 */
public class WebSocketOperator implements SocketOperatorApi {

	@Override
	public void sendMsgOfUserSessionBySessionId(String msgType, String sessionId, Object msg) throws SocketException {
		SocketSession<KernelSocketOperator> session = SessionCenter.getSessionBySessionId(sessionId);
		if (ObjectUtil.isEmpty(session)) {
			throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
		}
		WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
		webSocketMessageDTO.setData(msg);
		webSocketMessageDTO.setServerMsgType(msgType);
		session.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
	}

	@Override
	public void sendMsgOfUserSession(String msgType, String userId, Object msg) throws SocketException {
		// 根据用户ID获取会话
		List<SocketSession<KernelSocketOperator>> socketSessionList = SessionCenter
				.getSessionByUserIdAndMsgType(userId);
		if (ObjectUtil.isEmpty(socketSessionList)) {
			throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
		}
		WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
		webSocketMessageDTO.setData(msg);
		webSocketMessageDTO.setServerMsgType(msgType);
		for (SocketSession<KernelSocketOperator> session : socketSessionList) {
			// 发送内容
			session.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
		}
	}

	@Override
	public void sendMsgOfAllUserSession(String msgType, Object msg) {
		Collection<List<SocketSession<KernelSocketOperator>>> values = SessionCenter.getSocketSessionMap().values();
		WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
		webSocketMessageDTO.setData(msg);
		webSocketMessageDTO.setServerMsgType(msgType);
		for (List<SocketSession<KernelSocketOperator>> sessions : values) {
			for (SocketSession<KernelSocketOperator> session : sessions) {
				// 找到该类型的通道
				if (session.getMessageType().equals(msgType)) {
					session.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
				}
			}
		}
	}

	@Override
	public void closeSocketBySocketId(String socketId) {
		SessionCenter.closed(socketId);
	}

	@Override
	public void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface) {
		SocketMessageCenter.setMessageListener(msgType, callbackInterface);
	}

}
