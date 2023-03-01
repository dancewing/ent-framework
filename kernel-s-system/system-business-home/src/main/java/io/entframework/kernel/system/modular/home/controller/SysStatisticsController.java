/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.controller;

import java.util.List;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsCountRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsCountResponse;
import io.entframework.kernel.system.modular.home.service.SysStatisticsCountService;
import jakarta.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.entframework.kernel.db.api.pojo.page.PageResult;

/**
 * 常用功能的统计次数控制器
 *
 * @date 2022/02/10 21:17
 */
@RestController
@ApiResource(name = "常用功能的统计次数")
public class SysStatisticsController {

    @Resource
    private SysStatisticsCountService sysStatisticsCountService;

    /**
     * 添加
     *
     * @date 2022/02/10 21:17
     */
    @PostResource(name = "添加", path = "/sys-statistics-count/add")
    public ResponseData<Void> add(
            @RequestBody @Validated(BaseRequest.add.class) SysStatisticsCountRequest sysStatisticsCountRequest) {
        sysStatisticsCountService.add(sysStatisticsCountRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除
     *
     * @date 2022/02/10 21:17
     */
    @PostResource(name = "删除", path = "/sys-statistics-count/delete")
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) SysStatisticsCountRequest sysStatisticsCountRequest) {
        sysStatisticsCountService.del(sysStatisticsCountRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新
     *
     * @date 2022/02/10 21:17
     */
    @PostResource(name = "编辑", path = "/sys-statistics-count/update")
    public ResponseData<Void> update(
            @RequestBody @Validated(BaseRequest.update.class) SysStatisticsCountRequest sysStatisticsCountRequest) {
        sysStatisticsCountService.update(sysStatisticsCountRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看详情
     *
     * @date 2022/02/10 21:17
     */
    @GetResource(name = "查看详情", path = "/sys-statistics-count/detail")
    public ResponseData<SysStatisticsCountResponse> detail(
            @Validated(BaseRequest.detail.class) SysStatisticsCountRequest sysStatisticsCountRequest) {
        return ResponseData.ok(sysStatisticsCountService.detail(sysStatisticsCountRequest));
    }

    /**
     * 获取列表
     *
     * @date 2022/02/10 21:17
     */
    @GetResource(name = "获取列表", path = "/sys-statistics-count/list")
    public ResponseData<List<SysStatisticsCountResponse>> list(SysStatisticsCountRequest sysStatisticsCountRequest) {
        return ResponseData.ok(sysStatisticsCountService.findList(sysStatisticsCountRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @date 2022/02/10 21:17
     */
    @GetResource(name = "分页查询", path = "/sys-statistics-count/page")
    public ResponseData<PageResult<SysStatisticsCountResponse>> page(
            SysStatisticsCountRequest sysStatisticsCountRequest) {
        return ResponseData.ok(sysStatisticsCountService.findPage(sysStatisticsCountRequest));
    }

}
