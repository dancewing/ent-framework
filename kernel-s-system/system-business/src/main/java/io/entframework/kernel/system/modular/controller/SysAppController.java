/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.request.SysAppRequest;
import io.entframework.kernel.system.api.pojo.response.SysAppResponse;
import io.entframework.kernel.system.modular.entity.SysApp;
import io.entframework.kernel.system.modular.service.SysAppService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统应用控制器
 *
 * @date 2020/3/20 21:25
 */
@RestController
@ApiResource(name = "系统应用")
public class SysAppController {

    @Resource
    private SysAppService sysAppService;

    /**
     * 添加系统应用
     *
     * @date 2020/3/25 14:44
     */
    @PostResource(name = "添加系统应用", path = "/sys-app/add")
    public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysAppRequest sysAppParam) {
        sysAppService.add(sysAppParam);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统应用
     *
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "删除系统应用", path = "/sys-app/delete")
    public ResponseData<Void> delete(@RequestBody @Validated(BaseRequest.delete.class) SysAppRequest sysAppParam) {
        sysAppService.del(sysAppParam);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新系统应用
     *
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "更新系统应用", path = "/sys-app/update")
    public ResponseData<Void> update(@RequestBody @Validated(BaseRequest.update.class) SysAppRequest sysAppParam) {
        sysAppService.update(sysAppParam);
        return ResponseData.OK_VOID;
    }

    /**
     * 修改应用状态
     *
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "修改应用状态", path = "/sys-app/update-status")
    public ResponseData<Void> updateStatus(
            @RequestBody @Validated(BaseRequest.updateStatus.class) SysAppRequest sysAppParam) {
        sysAppService.updateStatus(sysAppParam);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看系统应用
     *
     * @date 2020/3/26 9:49
     */
    @GetResource(name = "查看系统应用", path = "/sys-app/detail")
    public ResponseData<SysAppResponse> detail(@Validated(BaseRequest.detail.class) SysAppRequest sysAppParam) {
        return ResponseData.ok(sysAppService.detail(sysAppParam));
    }

    /**
     * 系统应用列表
     *
     * @date 2020/4/19 14:55
     */
    @GetResource(name = "系统应用列表", path = "/sys-app/list")
    public ResponseData<List<SysAppResponse>> list(SysAppRequest sysAppParam) {
        return ResponseData.ok(sysAppService.findList(sysAppParam));
    }

    /**
     * 查询系统应用
     *
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "查询系统应用", path = "/sys-app/page")
    public ResponseData<PageResult<SysAppResponse>> page(SysAppRequest sysAppParam) {
        return ResponseData.ok(sysAppService.findPage(sysAppParam));
    }

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "设为默认应用", path = "/sys-app/update-active-flag")
    public ResponseData<Void> setAsDefault(
            @RequestBody @Validated(SysAppRequest.updateActiveFlag.class) SysAppRequest sysAppParam) {
        sysAppService.updateActiveFlag(sysAppParam);
        return ResponseData.OK_VOID;
    }

    /**
     * 获取应用列表，用于顶部应用列表
     *
     * @date 2021/4/21 15:31
     */
    @GetResource(name = "获取应用列表，用于顶部应用列表", path = "/sys-menu/get-top-app-list", requiredPermission = false)
    public ResponseData<List<SysApp>> getTopAppList() {
        List<SysApp> userTopAppList = sysAppService.getUserTopAppList();
        return ResponseData.ok(userTopAppList);
    }

}
