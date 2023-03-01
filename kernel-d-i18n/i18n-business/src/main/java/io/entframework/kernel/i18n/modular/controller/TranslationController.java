/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.i18n.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.i18n.api.pojo.request.TranslationRequest;
import io.entframework.kernel.i18n.api.pojo.response.TranslationResponse;
import io.entframework.kernel.i18n.modular.service.TranslationService;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多语言接口控制器
 *
 * @date 2021/1/24 19:18
 */
@RestController
@ApiResource(name = "多语言接口控制器")
public class TranslationController {

    @Resource
    private TranslationService translationService;

    /**
     * 新增多语言翻译记录
     *
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/add")
    public ResponseData<Void> add(
            @RequestBody @Validated(BaseRequest.add.class) TranslationRequest translationRequest) {
        this.translationService.add(translationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 编辑多语言翻译记录
     *
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "更新多语言配置", path = "/i18n/update")
    public ResponseData<Void> update(
            @RequestBody @Validated(BaseRequest.update.class) TranslationRequest translationRequest) {
        this.translationService.update(translationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除多语言配置
     *
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "删除多语言配置", path = "/i18n/delete")
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) TranslationRequest translationRequest) {
        this.translationService.del(translationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除某个语种
     *
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "删除某个语种", path = "/i18n/delete-language")
    public ResponseData<Void> deleteTranLanguage(
            @RequestBody @Validated(TranslationRequest.deleteTranLanguage.class) TranslationRequest translationRequest) {
        this.translationService.deleteTranLanguage(translationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看多语言详情
     *
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/detail")
    public ResponseData<TranslationResponse> detail(
            @Validated(BaseRequest.detail.class) TranslationRequest translationRequest) {
        TranslationResponse detail = this.translationService.detail(translationRequest);
        return ResponseData.ok(detail);
    }

    /**
     * 查看多语言配置列表
     *
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/page")
    public ResponseData<PageResult<TranslationResponse>> page(TranslationRequest translationRequest) {
        PageResult<TranslationResponse> page = this.translationService.findPage(translationRequest);
        return ResponseData.ok(page);
    }

}
