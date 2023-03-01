/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeTemplateFieldResponse;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateField;

import java.util.List;

/**
 * 系统主题模板属性service接口
 *
 * @date 2021/12/17 10:30
 */
public interface SysThemeTemplateFieldService
        extends BaseService<SysThemeTemplateFieldRequest, SysThemeTemplateFieldResponse, SysThemeTemplateField> {

    /**
     * 增加系统主题模板属性
     *
     * @date 2021/12/17 10:47
     */
    void add(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 删除系统主题模板属性
     *
     * @date 2021/12/17 11:00
     */
    void del(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 修改系统主题模板属性
     *
     * @date 2021/12/17 11:29
     */
    SysThemeTemplateFieldResponse update(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 查询系统主题模板属性详情
     *
     * @date 2021/12/17 11:47
     */
    SysThemeTemplateFieldResponse detail(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 查询系统主题模板属性列表
     * @return 分页结果
     * @date 2021/12/24 9:29
     */
    PageResult<SysThemeTemplateFieldResponse> findPage(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 查询系统主题模板属性已有关系列表
     *
     * @date 2021/12/24 11:35
     */
    List<SysThemeTemplateFieldResponse> findRelList(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 查询系统主题模板属性未有关系列表
     *
     * @date 2021/12/24 11:49
     */
    List<SysThemeTemplateFieldResponse> findNotRelList(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 根据字段名，获取该属性是否为文件类型
     *
     * @date 2022/1/1 22:24
     */
    boolean getKeyFileFlag(String code);

}
