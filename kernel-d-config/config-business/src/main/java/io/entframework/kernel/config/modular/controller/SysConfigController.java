/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.config.modular.controller;

import io.entframework.kernel.config.api.pojo.ConfigInitItem;
import io.entframework.kernel.config.api.pojo.ConfigInitRequest;
import io.entframework.kernel.config.modular.entity.SysConfig;
import io.entframework.kernel.config.modular.pojo.request.SysConfigRequest;
import io.entframework.kernel.config.modular.pojo.response.SysConfigResponse;
import io.entframework.kernel.config.modular.service.SysConfigService;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 参数配置控制器
 *
 * @date 2020/4/13 22:46
 */
@RestController
@ApiResource(name = "参数配置控制器")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 添加系统参数配置
     *
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "添加系统参数配置", path = "/sys-config/add")
    public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysConfigRequest sysConfigRequest) {
        sysConfigService.add(sysConfigRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统参数配置
     *
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "删除系统参数配置", path = "/sys-config/delete")
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) SysConfigRequest sysConfigRequest) {
        sysConfigService.del(sysConfigRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 编辑系统参数配置
     *
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "编辑系统参数配置", path = "/sys-config/update")
    public ResponseData<Void> update(
            @RequestBody @Validated(BaseRequest.update.class) SysConfigRequest sysConfigRequest) {
        sysConfigService.update(sysConfigRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看系统参数配置
     *
     * @date 2020/4/14 11:12
     */
    @GetResource(name = "查看系统参数配置", path = "/sys-config/detail")
    public ResponseData<SysConfig> detail(@Validated(BaseRequest.detail.class) SysConfigRequest sysConfigRequest) {
        return ResponseData.ok(sysConfigService.detail(sysConfigRequest));
    }

    /**
     * 分页查询配置列表
     *
     * @date 2020/4/14 11:10
     */
    @GetResource(name = "分页查询配置列表", path = "/sys-config/page")
    public ResponseData<PageResult<SysConfigResponse>> page(SysConfigRequest sysConfigRequest) {
        return ResponseData.ok(sysConfigService.findPage(sysConfigRequest));
    }

    /**
     * 系统参数配置列表
     *
     * @date 2020/4/14 11:10
     */
    @GetResource(name = "系统参数配置列表", path = "/sys-config/list")
    public ResponseData<List<SysConfigResponse>> list(SysConfigRequest sysConfigRequest) {
        return ResponseData.ok(sysConfigService.findList(sysConfigRequest));
    }

    /**
     * 获取系统配置是否初始化的标志
     *
     * @date 2021/7/8 17:20
     */
    @GetResource(name = "获取系统配置是否初始化的标志", path = "/sys-config/get-init-config-flag", requiredPermission = false)
    public ResponseData<Boolean> getInitConfigFlag() {
        return ResponseData.ok(sysConfigService.getInitConfigFlag());
    }

    /**
     * 初始化系统配置参数，用在系统第一次登录时
     *
     * @date 2021/7/8 16:36
     */
    @PostResource(name = "初始化系统配置参数，用在系统第一次登录时", path = "/sys-config/init-config", requiredPermission = false)
    public ResponseData<Void> initConfig(@RequestBody ConfigInitRequest configInitRequest) {
        sysConfigService.initConfig(configInitRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 获取需要初始化的配置列表
     *
     * @date 2021/7/8 16:36
     */
    @GetResource(name = "获取需要初始化的配置列表", path = "/sys-config/get-init-config-list")
    public ResponseData<List<ConfigInitItem>> getInitConfigList() {
        return ResponseData.ok(sysConfigService.getInitConfigs());
    }

    /**
     * 获取后端服务部署的地址
     *
     * @date 2021/7/8 16:36
     */
    @GetResource(name = "获取后端服务部署的地址", path = "/sys-config/get-backend-deploy-url", requiredLogin = false,
            requiredPermission = false)
    public ResponseData<String> getBackendDeployUrl() {
        String serverDeployHost = sysConfigService.getServerDeployHost();
        return ResponseData.ok(serverDeployHost);
    }

}
