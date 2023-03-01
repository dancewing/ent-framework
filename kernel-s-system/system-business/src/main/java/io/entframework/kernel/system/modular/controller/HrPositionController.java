/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.request.HrPositionRequest;
import io.entframework.kernel.system.api.pojo.response.HrPositionResponse;
import io.entframework.kernel.system.modular.service.HrPositionService;

/**
 * 系统职位控制器
 *
 * @date 2020/11/18 21:56
 */
@RestController
@ApiResource(name = "系统职位管理")
public class HrPositionController {

    @Resource
    private HrPositionService hrPositionService;

    /**
     * 添加系统职位
     *
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "添加系统职位", path = "/hr-position/add")
    public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.add(hrPositionRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统职位
     *
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "删除系统职位", path = "/hr-position/delete")
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.del(hrPositionRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 批量删除系统职位
     *
     * @date 2021/4/8 13:50
     */
    @PostResource(name = "批量删除系统职位", path = "/hr-position/batch-delete")
    public ResponseData<Void> batchDelete(
            @RequestBody @Validated(BaseRequest.batchDelete.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.batchDel(hrPositionRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新系统职位
     *
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "更新系统职位", path = "/hr-position/update")
    public ResponseData<Void> update(
            @RequestBody @Validated(BaseRequest.update.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.update(hrPositionRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新职位状态
     *
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "更新职位状态", path = "/hr-position/update-status")
    public ResponseData<Void> updateStatus(
            @RequestBody @Validated(BaseRequest.updateStatus.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.changeStatus(hrPositionRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看详情系统职位
     *
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "查看详情系统职位", path = "/hr-position/detail")
    public ResponseData<HrPositionResponse> detail(
            @Validated(BaseRequest.detail.class) HrPositionRequest hrPositionRequest) {
        return ResponseData.ok(hrPositionService.detail(hrPositionRequest));
    }

    /**
     * 分页查询系统职位
     *
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "分页查询系统职位", path = "/hr-position/page")
    public ResponseData<PageResult<HrPositionResponse>> page(HrPositionRequest hrPositionRequest) {
        return ResponseData.ok(hrPositionService.findPage(hrPositionRequest));
    }

    /**
     * 获取全部系统职位
     *
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "获取全部系统职位", path = "/hr-position/list")
    public ResponseData<List<HrPositionResponse>> list(HrPositionRequest hrPositionRequest) {
        return ResponseData.ok(hrPositionService.findList(hrPositionRequest));
    }

}
