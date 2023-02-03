/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api.factory.appender;

import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 日志信息追加，用来追加用户的登录信息
 *
 * @date 2020/10/27 17:45
 */
@Slf4j
public class AuthedLogAppender {

    /**
     * 填充token和userId字段
     * <p>
     * 但是此方法会依赖auth-api模块，所以用这个方法得引入auth模块
     *
     * @date 2020/10/27 18:22
     */
    public static void appendAuthedHttpLog(LogRecordDTO logRecordDTO) {

        // 填充当前登录的用户信息
        try {
            // 填充登录用户的token
            logRecordDTO.setToken(LoginContext.me().getToken());

            // 填充登录用户的userId
            LoginUser loginUser = LoginContext.me().getLoginUser();
            logRecordDTO.setUserId(loginUser.getUserId());
            logRecordDTO.setCreateUser(loginUser.getUserId());
            logRecordDTO.setCreateUserName(loginUser.getAccount());
            logRecordDTO.setCreateTime(LocalDateTime.now());
        } catch (Exception ex) {
            // 获取不到用户登录信息，就不填充
            log.info("Can't fill user info to http record log: {}", ex.getMessage());
        }

    }

}
