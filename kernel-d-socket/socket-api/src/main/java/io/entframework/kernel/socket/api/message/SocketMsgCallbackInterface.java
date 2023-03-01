/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.socket.api.message;

import io.entframework.kernel.socket.api.session.pojo.SocketSession;

/**
 * Socket消息接收回调接口
 *
 * @date 2021/6/2 上午9:53
 */
@FunctionalInterface
public interface SocketMsgCallbackInterface {

    /**
     * 收到消息的回调
     * @param msgType 消息类型
     * @param msg 消息体
     * @param socketSession 本次通信的会话
     * @date 2021/6/2 上午9:51
     **/
    void callback(String msgType, Object msg, SocketSession socketSession);

}
