/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.dict.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.dict.api.constants.DictConstants;
import io.entframework.kernel.dict.modular.pojo.request.SysDictTypeRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictTypeResponse;
import io.entframework.kernel.dict.modular.service.SysDictTypeService;
import io.entframework.kernel.rule.annotation.BusinessLog;
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
 * 字典类型管理
 *
 * @date 2020/10/30 21:46
 */
@RestController
@ApiResource(name = "字典类型管理")
public class DictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 添加字典类型
     *
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "添加字典类型", path = "/dict-type/add", requiredPermission = false)
    @BusinessLog
    public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysDictTypeRequest dictTypeRequest) {
        this.sysDictTypeService.add(dictTypeRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除字典类型
     *
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "删除字典类型", path = "/dict-type/delete", requiredPermission = false)
    @BusinessLog
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) SysDictTypeRequest dictTypeRequest) {
        this.sysDictTypeService.del(dictTypeRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新字典类型
     *
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "更新字典类型", path = "/dict-type/update", requiredPermission = false)
    @BusinessLog
    public ResponseData<Void> update(
            @RequestBody @Validated(BaseRequest.update.class) SysDictTypeRequest dictTypeRequest) {
        this.sysDictTypeService.update(dictTypeRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 修改字典类型状态
     *
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型状态", path = "/dict-type/update-status", requiredPermission = false)
    public ResponseData<Void> updateStatus(
            @RequestBody @Validated(BaseRequest.updateStatus.class) SysDictTypeRequest dictTypeRequest) {
        this.sysDictTypeService.updateStatus(dictTypeRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 获取字典类型详情
     *
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取字典类型详情", path = "/dict-type/detail", requiredPermission = false)
    public ResponseData<SysDictTypeResponse> detail(
            @Validated(BaseRequest.detail.class) SysDictTypeRequest dictTypeRequest) {
        SysDictTypeResponse detail = this.sysDictTypeService.detail(dictTypeRequest);
        return ResponseData.ok(detail);
    }

    /**
     * 获取字典类型列表
     *
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表", path = "/dict-type/list", requiredPermission = false)
    public ResponseData<List<SysDictTypeResponse>> list(SysDictTypeRequest dictTypeRequest) {
        return ResponseData.ok(sysDictTypeService.findList(dictTypeRequest));
    }

    /**
     * 获取字典类型列表(分页)
     *
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表(分页)", path = "/dict-type/page", requiredPermission = false)
    public ResponseData<PageResult<SysDictTypeResponse>> page(SysDictTypeRequest dictTypeRequest) {
        return ResponseData.ok(sysDictTypeService.findPage(dictTypeRequest));
    }

    /**
     * 获取字典类型详情
     *
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取系统配置字典类型详情", path = "/dict-type/config-dict-type-detail", requiredPermission = false)
    public ResponseData<SysDictTypeResponse> getConfigDictTypeDetail(SysDictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.CONFIG_GROUP_DICT_TYPE_CODE);
        SysDictTypeResponse detail = this.sysDictTypeService.detail(dictTypeRequest);
        return ResponseData.ok(detail);
    }

    /**
     * 获取字典类型详情
     *
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取语种字典类型型详情", path = "/dict-type/translation-detail", requiredPermission = false)
    public ResponseData<SysDictTypeResponse> getTranslationDetail(SysDictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        SysDictTypeResponse detail = this.sysDictTypeService.detail(dictTypeRequest);
        return ResponseData.ok(detail);
    }

}
