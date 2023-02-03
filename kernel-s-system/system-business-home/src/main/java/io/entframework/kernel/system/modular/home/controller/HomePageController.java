/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.controller;

import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.system.api.pojo.home.HomeCompanyInfo;
import io.entframework.kernel.system.api.pojo.user.OnlineUserRequest;
import io.entframework.kernel.system.modular.home.pojo.OnlineUserStat;
import io.entframework.kernel.system.modular.home.service.HomePageService;
import io.entframework.kernel.system.modular.entity.SysMenu;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 首页控制器
 *
 * @date 2022/1/25 9:44
 */
@RestController
@ApiResource(name = "首页")
public class HomePageController {

    @Resource
    private HomePageService homePageService;

    /**
     * 查询在线用户列表
     *
     * @date 2022/1/25 14:11
     */
    @GetResource(name = "查询在线用户列表", path = "/home-page/get-online-user-list", requiredPermission = false)
    public ResponseData<OnlineUserStat> getOnlineUserList(OnlineUserRequest onlineUserRequest) {
        return ResponseData.ok(homePageService.getOnlineUserList(onlineUserRequest));
    }

    /**
     * 获取首页企业和公司信息
     *
     * @date 2022/2/9 10:12
     */
    @GetResource(name = "获取首页企业和公司信息", path = "/home-page/get-home-company-info", requiredPermission = false)
    public ResponseData<HomeCompanyInfo> getHomeCompanyInfo() {
        return ResponseData.ok(homePageService.getHomeCompanyInfo());
    }

    /**
     * 获取常用功能接口
     *
     * @date 2022/2/10 11:34
     */
    @GetResource(name = "获取常用功能接口", path = "/home-page/get-common-functions", requiredPermission = false)
    public ResponseData<List<SysMenu>> getCommonFunctions() {
        return ResponseData.ok(homePageService.getCommonFunctions());
    }
}
