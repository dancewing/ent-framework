/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.manage;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.log.api.LogManagerApi;
import io.entframework.kernel.log.api.pojo.manage.SysLogRequest;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import io.entframework.kernel.log.db.service.SysLogService;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 日志管理控制器
 *
 * @date 2020/11/3 12:44
 */
@RestController
@ApiResource(name = "日志管理控制器")
public class LogManagerController {

    /**
     * 日志管理api
     */
    @Resource
    private LogManagerApi logManagerApi;

    /**
     * 日志管理service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 查询日志列表
     *
     * @date 2020/11/3 12:58
     */
    @GetResource(name = "查询日志列表", path = "/log-manager/list")
    public ResponseData<List<LogRecordDTO>> list(@RequestBody SysLogRequest sysLogRequest) {
        return ResponseData.ok(logManagerApi.findList(sysLogRequest));
    }

    /**
     * 查询日志
     *
     * @date 2021/1/8 17:36
     */
    @GetResource(name = "查询日志列表", path = "/log-manager/page")
    public ResponseData<PageResult<LogRecordDTO>> page(SysLogRequest sysLogRequest) {
        return ResponseData.ok(logManagerApi.findPage(sysLogRequest));
    }

    /**
     * 删除日志
     *
     * @date 2020/11/3 13:47
     */
    @PostResource(name = "删除日志", path = "/log-manager/delete")
    public ResponseData<Void> delete(@RequestBody @Validated(BaseRequest.delete.class) SysLogRequest sysLogRequest) {
        sysLogService.delAll(sysLogRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看日志详情
     *
     * @date 2021/1/11 17:36
     */
    @GetResource(name = "查看日志详情", path = "/log-manager/detail")
    public ResponseData<LogRecordDTO> detail(@Validated(BaseRequest.detail.class) SysLogRequest sysLogRequest) {
        return ResponseData.ok(logManagerApi.detail(sysLogRequest));
    }

}
