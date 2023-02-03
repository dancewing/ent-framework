/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.socket.business.websocket.session;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.socket.api.session.pojo.SocketSession;
import io.entframework.kernel.socket.business.websocket.operator.channel.KernelSocketOperator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 会话中心
 * <p>
 * 维护所有的会话
 *
 * @date 2021/6/1 下午1:43
 */
public class SessionCenter {

    /**
     * 所有用户会话维护
     */
    private static ConcurrentMap<String, List<SocketSession<KernelSocketOperator>>> socketSessionMap = new ConcurrentHashMap<>();

    /**
     * 获取维护的所有会话
     * @date 2021/6/1 下午2:13
     **/
    public static ConcurrentMap<String, List<SocketSession<KernelSocketOperator>>> getSocketSessionMap() {
        return socketSessionMap;
    }

    /**
     * 根据用户ID获取会话信息列表
     *
     * @param userId 用户ID
     * @return {@link SocketSession <KernelSocketOperator>}
     * @date 2021/6/1 下午1:48
     **/
    public static List<SocketSession<KernelSocketOperator>> getSessionByUserId(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 根据用户ID和消息类型获取会话信息列表
     *
     * @param userId 用户ID
     * @return {@link SocketSession <KernelSocketOperator>}
     * @date 2021/6/1 下午1:48
     **/
    public static List<SocketSession<KernelSocketOperator>> getSessionByUserIdAndMsgType(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 根据会话ID获取会话信息
     *
     * @param sessionId 会话ID
     * @return {@link SocketSession <KernelSocketOperator>}
     * @date 2021/6/1 下午1:48
     **/
    public static SocketSession<KernelSocketOperator> getSessionBySessionId(String sessionId) {
        Set<Map.Entry<String, List<SocketSession<KernelSocketOperator>>>> entrySet = socketSessionMap.entrySet();
        for (Map.Entry<String, List<SocketSession<KernelSocketOperator>>> stringListEntry : entrySet) {
            List<SocketSession<KernelSocketOperator>> stringListEntryValue = stringListEntry.getValue();
            for (SocketSession<KernelSocketOperator> socketSession : stringListEntryValue) {
                if (sessionId.equals(socketSession.getSessionId())) {
                    return socketSession;
                }
            }
        }
        return null;
    }

    /**
     * 设置会话
     *
     * @param socketSession 会话详情
     * @date 2021/6/1 下午1:49
     **/
    public static void addSocketSession(SocketSession<KernelSocketOperator> socketSession) {
        List<SocketSession<KernelSocketOperator>> socketSessions = socketSessionMap.get(socketSession.getUserId());
        if (ObjectUtil.isEmpty(socketSessions)) {
            socketSessions = Collections.synchronizedList(new ArrayList<>());
            socketSessionMap.put(socketSession.getUserId(), socketSessions);
        }
        socketSessions.add(socketSession);
    }

    /**
     * 连接关闭
     *
     * @param sessionId 会话ID
     * @date 2021/6/1 下午3:25
     **/
    public static void closed(String sessionId) {
        // 删除维护关系
        SocketSession<KernelSocketOperator> operatorSocketSession = getSessionBySessionId(sessionId);
        if (operatorSocketSession != null && operatorSocketSession.getSocketOperatorApi() != null) {
            operatorSocketSession.getSocketOperatorApi().close();
        }
    }

    /**
     * 删除维护关系
     *
     * @param sessionId 会话ID
     * @return {@link SocketSession < KernelSocketOperator >}
     * @date 2021/8/30 9:20
     **/
    public static SocketSession<KernelSocketOperator> deleteById(String sessionId) {
        // 获取所有人员的连接会话信息
        Set<Map.Entry<String, List<SocketSession<KernelSocketOperator>>>> entrySet = socketSessionMap.entrySet();
        Iterator<Map.Entry<String, List<SocketSession<KernelSocketOperator>>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<SocketSession<KernelSocketOperator>>> next = iterator.next();
            List<SocketSession<KernelSocketOperator>> value = next.getValue();
            if (ObjectUtil.isNotEmpty(value)) {

                // 找出该人员的指定会话信息
                Iterator<SocketSession<KernelSocketOperator>> socketSessionIterator = value.iterator();
                while (socketSessionIterator.hasNext()) {
                    SocketSession<KernelSocketOperator> operatorSocketSession = socketSessionIterator.next();
                    if (operatorSocketSession.getSessionId().equals(sessionId)) {
                        // 在列表中删除
                        socketSessionIterator.remove();
                        return operatorSocketSession;
                    }
                }
            }
        }

        return null;
    }
}
