/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.controller;

import io.entframework.kernel.rule.annotation.BusinessLog;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 系统主题模板属性关系控制器
 *
 * @date 2021/12/24 10:55
 */
@RestController
@ApiResource(name = "系统主题模板属性关系管理")
public class SysThemeTemplateRelController {

    @Resource
    private SysThemeTemplateRelService sysThemeTemplateRelService;

    /**
     * 增加系统主题模板属性关系
     *
     * @date 2021/12/24 11:17
     */
    @PostResource(name = "增加系统主题模板属性关系", path = "/sys-theme-template-rel/add")
    @BusinessLog
    public ResponseData<Void> add(
            @RequestBody @Validated(BaseRequest.add.class) SysThemeTemplateRelRequest sysThemeTemplateParam) {
        sysThemeTemplateRelService.add(sysThemeTemplateParam);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统主题模板属性关系
     *
     * @date 2021/12/24 11:23
     */
    @PostResource(name = "删除系统主题模板属性关系", path = "/sys-theme-template-rel/del")
    @BusinessLog
    public ResponseData<Void> del(@RequestBody SysThemeTemplateRelRequest sysThemeTemplateRelParam) {
        sysThemeTemplateRelService.del(sysThemeTemplateRelParam);
        return ResponseData.OK_VOID;
    }

}
