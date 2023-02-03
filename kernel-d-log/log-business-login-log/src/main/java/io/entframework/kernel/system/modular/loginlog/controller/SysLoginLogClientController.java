/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.loginlog.controller;

import io.entframework.kernel.log.api.LoginLogServiceApi;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 登陆日志控制器
 *
 * @date 2021/1/13 17:51
 */
@RestController
public class SysLoginLogClientController {
    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @PostMapping(path = "/client/login-log/add")
    public boolean add(@RequestBody SysLoginLogRequest request) {
        return this.loginLogServiceApi.add(request);
    }
}
