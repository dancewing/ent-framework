/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.service.BaseService;
import io.entframework.kernel.system.api.ThemeServiceApi;
import io.entframework.kernel.system.api.pojo.theme.DefaultTheme;
import io.entframework.kernel.system.api.pojo.theme.SysThemeRequest;
import io.entframework.kernel.system.api.pojo.theme.SysThemeResponse;
import io.entframework.kernel.system.modular.theme.entity.SysTheme;

/**
 * 系统主题service接口
 *
 * @date 2021/12/17 16:15
 */
public interface SysThemeService extends BaseService<SysThemeRequest, SysThemeResponse, SysTheme>, ThemeServiceApi {

    /**
     * 增加系统主题
     *
     * @date 2021/12/17 16:26
     */
    void add(SysThemeRequest sysThemeRequest);

    /**
     * 删除系统主题
     *
     * @date 2021/12/17 16:29
     */
    void del(SysThemeRequest sysThemeRequest);

    /**
     * 修改系统主题
     *
     * @date 2021/12/17 16:47
     */
    SysThemeResponse update(SysThemeRequest sysThemeRequest);

    /**
     * 查询系统主题
     *
     * @return 分页结果
     * @date 2021/12/17 16:52
     */
    PageResult<SysThemeResponse> findPage(SysThemeRequest sysThemeRequest);

    /**
     * 查询系统主题详情
     *
     * @return 系统主题
     * @date 2021/12/17 17:01
     */
    SysThemeResponse detail(SysThemeRequest sysThemeRequest);

    /**
     * 修改系统主题启用状态
     *
     * @date 2021/12/17 17:06
     */
    void updateThemeStatus(SysThemeRequest sysThemeRequest);

    /**
     * 当前系统主题数据
     *
     * @date 2022/1/10 18:30
     */
    DefaultTheme currentThemeInfo(SysThemeRequest sysThemeParam);

}
