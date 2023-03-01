/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.socket.business.websocket.controller;

import io.entframework.kernel.socket.api.SocketOperatorApi;
import io.entframework.kernel.socket.api.exception.SocketException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
public class SocketClientController {

    @Resource
    private SocketOperatorApi socketOperatorApi;

    @PostMapping(path = "/client/sys-socket/send-msg-to-user-session")
    public void sendMsgOfUserSession(String msgType, String userId, Object msg) throws SocketException {
        socketOperatorApi.sendMsgOfUserSession(msgType, userId, msg);
    }

}
