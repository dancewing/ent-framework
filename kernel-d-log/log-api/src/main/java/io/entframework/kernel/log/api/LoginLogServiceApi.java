/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api;


import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 登录日志api接口
 *
 * @date 2021/1/13 11:12
 */
public interface LoginLogServiceApi {
    @PostMapping(path = "/client/login-log/add")
    boolean add(@RequestBody SysLoginLogRequest request);
}
