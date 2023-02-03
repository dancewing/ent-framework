/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.loginlog.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogResponse;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.system.modular.loginlog.service.SysLoginLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 登陆日志控制器
 *
 * @date 2021/1/13 17:51
 */
@RestController
@ApiResource(name = "登录日志")
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    /**
     * 清空登录日志
     *
     * @date 2021/1/13 17:51
     */
    @GetResource(name = "清空登录日志", path = "/login-log/delete-all")
    public ResponseData<Void> deleteAll() {
        sysLoginLogService.delAll();
        return ResponseData.OK_VOID;
    }

    /**
     * 查询登录日志详情
     *
     * @date 2021/1/13 17:51
     */
    @GetResource(name = "查看详情登录日志", path = "/login-log/detail")
    public ResponseData<SysLoginLogResponse> detail(@Validated(BaseRequest.detail.class) SysLoginLogRequest sysLoginLogRequest) {
        return ResponseData.ok(sysLoginLogService.detail(sysLoginLogRequest));
    }

    /**
     * 分页查询登录日志
     *
     * @date 2021/1/13 17:51
     */
    @GetResource(name = "分页查询登录日志", path = "/login-log/page")
    public ResponseData<PageResult<SysLoginLogResponse>> page(SysLoginLogRequest sysLoginLogRequest) {
        return ResponseData.ok(sysLoginLogService.findPage(sysLoginLogRequest));
    }
}
