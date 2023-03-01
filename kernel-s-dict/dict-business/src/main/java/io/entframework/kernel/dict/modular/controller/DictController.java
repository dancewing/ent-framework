/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.dict.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.dict.api.constants.DictConstants;
import io.entframework.kernel.dict.modular.pojo.TreeDictInfo;
import io.entframework.kernel.dict.modular.pojo.request.SysDictRequest;
import io.entframework.kernel.dict.modular.pojo.response.SysDictResponse;
import io.entframework.kernel.dict.modular.service.SysDictService;
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
 * 字典详情管理，具体管理某个字典类型下的条目
 *
 * @date 2020/10/29 14:45
 */
@RestController
@ApiResource(name = "字典详情管理")
public class DictController {

    @Resource
    private SysDictService sysDictService;

    /**
     * 添加字典条目
     *
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "添加字典", path = "/dict/add", requiredPermission = false)
    public ResponseData<Void> add(@RequestBody @Validated(BaseRequest.add.class) SysDictRequest dictRequest) {
        this.sysDictService.add(dictRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除字典条目
     *
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "删除字典", path = "/dict/delete", requiredPermission = false)
    @BusinessLog
    public ResponseData<Void> delete(@RequestBody @Validated(BaseRequest.delete.class) SysDictRequest dictRequest) {
        this.sysDictService.del(dictRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 修改字典条目
     *
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "更新字典", path = "/dict/update", requiredPermission = false)
    @BusinessLog
    public ResponseData<Void> update(@RequestBody @Validated(BaseRequest.update.class) SysDictRequest dictRequest) {
        this.sysDictService.update(dictRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 获取字典详情
     *
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典详情", path = "/dict/detail", requiredPermission = false)
    public ResponseData<SysDictResponse> detail(@Validated(BaseRequest.detail.class) SysDictRequest dictRequest) {
        SysDictResponse detail = this.sysDictService.detail(dictRequest);
        return ResponseData.ok(detail);
    }

    /**
     * 获取字典列表
     *
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典列表", path = "/dict/list", requiredPermission = false)
    public ResponseData<List<SysDictResponse>> list(SysDictRequest dictRequest) {
        return ResponseData.ok(this.sysDictService.findList(dictRequest));
    }

    /**
     * 获取字典列表(分页)
     *
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典列表", path = "/dict/page", requiredPermission = false)
    public ResponseData<PageResult<SysDictResponse>> page(SysDictRequest dictRequest) {
        return ResponseData.ok(this.sysDictService.findPage(dictRequest));
    }

    /**
     * 获取树形字典列表（antdv在用）
     *
     * @date 2020/10/29 16:36
     */
    @GetResource(name = "获取树形字典列表", path = "/dict/get-dict-tree-list", requiredPermission = false)
    public ResponseData<List<TreeDictInfo>> getDictTreeList(
            @Validated(SysDictRequest.treeList.class) SysDictRequest dictRequest) {
        List<TreeDictInfo> treeDictList = this.sysDictService.getTreeDictList(dictRequest);
        return ResponseData.ok(treeDictList);
    }

    /**
     * 获取系统配置分组字典列表(分页)（给系统配置界面，左侧获取配置的分类用）
     *
     * @date 2021/1/25 11:47
     */
    @GetResource(name = "获取系统配置分组字典列表", path = "/dict/get-config-group-page", requiredPermission = false)
    public ResponseData<PageResult<SysDictResponse>> getConfigGroupPage(SysDictRequest dictRequest) {
        dictRequest.setDictTypeCode(DictConstants.CONFIG_GROUP_DICT_TYPE_CODE);
        PageResult<SysDictResponse> page = this.sysDictService.findPage(dictRequest);
        return ResponseData.ok(page);
    }

    /**
     * 获取多语言字典列表(分页)（给多语言界面，左侧获取多语言的分类用）
     *
     * @date 2021/1/25 11:47
     */
    @GetResource(name = "获取多语言字典列表", path = "/dict/get-languages-page", requiredPermission = false)
    public ResponseData<PageResult<SysDictResponse>> getLanguagesPage(SysDictRequest dictRequest) {
        dictRequest.setDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        PageResult<SysDictResponse> page = this.sysDictService.findPage(dictRequest);
        return ResponseData.ok(page);
    }

}
