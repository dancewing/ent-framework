/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.socket.api.session;

/**
 * socket会话操作接口
 * <p>
 * 该接口面向会话，须基于会话的通道调用。
 * 该接口支持扩展，可参考WebSocket模块中{@link io.entframework.kernel.socket.websocket.operator.channel}包下的类
 *
 * @date 2021/6/1 上午11:46
 */
public interface SocketSessionOperatorApi {

    /**
     * 写出数据，经过责任链
     *
     * @date 2021/6/1 上午11:48
     **/
    void writeAndFlush(Object obj);

    /**
     * 写出数据，不经过责任链
     *
     * @date 2021/6/1 上午11:48
     **/
    void writeToChannel(Object obj);

    /**
     * 关闭会话
     *
     * @date 2021/6/1 上午11:48
     **/
    void close();

    /**
     * 是否存活
     * @return {@link boolean}
     * @date 2021/6/1 上午11:50
     **/
    boolean isInvalid();

}
