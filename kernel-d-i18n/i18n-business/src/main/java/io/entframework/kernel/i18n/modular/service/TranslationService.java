/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.i18n.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.i18n.api.TranslationPersistenceApi;
import io.entframework.kernel.i18n.api.pojo.request.TranslationRequest;
import io.entframework.kernel.i18n.api.pojo.response.TranslationResponse;
import io.entframework.kernel.i18n.modular.entity.Translation;

import java.util.List;

/**
 * 多语言表 服务类
 *
 * @date 2021/1/24 19:21
 */
public interface TranslationService extends BaseService<TranslationRequest, TranslationResponse, Translation>, TranslationPersistenceApi {

    /**
     * 新增
     *
     * @param translationRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void add(TranslationRequest translationRequest);

    /**
     * 删除
     *
     * @param translationRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void del(TranslationRequest translationRequest);

    /**
     * 修改
     *
     * @param translationRequest 参数对象
     * @date 2021/1/26 12:52
     */
    TranslationResponse update(TranslationRequest translationRequest);

    /**
     * 查询-详情-根据主键id
     *
     * @param translationRequest 参数对象
     * @date 2021/1/26 12:52
     */
    TranslationResponse detail(TranslationRequest translationRequest);

    /**
     * 查询-列表-按实体对象
     *
     * @param translationRequest 参数对象
     * @date 2021/1/26 12:52
     */
    List<TranslationResponse> findList(TranslationRequest translationRequest);

    /**
     * 查询-列表-分页-按实体对象
     *
     * @param translationRequest 参数对象
     * @date 2021/1/26 12:52
     */
    PageResult<TranslationResponse> findPage(TranslationRequest translationRequest);

    /**
     * 删除语种
     *
     * @param translationRequest 参数对象
     * @date 2021/1/30 10:00
     */
    void deleteTranLanguage(TranslationRequest translationRequest);

}
