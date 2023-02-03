/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.socket.business.websocket.operator.channel;

import com.alibaba.fastjson.JSON;

import jakarta.websocket.Session;

import java.io.IOException;

/**
 * Socket操作类实现
 * <p>
 * 简单封装Spring Boot的默认WebSocket
 *
 * @date 2021/6/1 下午3:41
 */
public class KernelSocketOperator implements SocketChannelExpandInterFace {

    /**
     * 实际操作的通道
     */
    private Session socketChannel;

    public KernelSocketOperator(Session socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void writeAndFlush(Object obj) {
        try {
            if (socketChannel.isOpen()) {
                socketChannel.getBasicRemote().sendText(JSON.toJSONString(obj));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToChannel(Object obj) {
        if (socketChannel.isOpen()) {
            socketChannel.getAsyncRemote().sendText(JSON.toJSONString(obj));
        }
    }

    @Override
    public void close() {
        try {
            if (socketChannel.isOpen()) {
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInvalid() {
        return socketChannel.isOpen();
    }
}
