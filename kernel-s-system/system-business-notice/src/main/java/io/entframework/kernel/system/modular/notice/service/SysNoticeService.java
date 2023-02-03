/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.notice.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.service.BaseService;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeRequest;
import io.entframework.kernel.system.api.pojo.notice.SysNoticeResponse;
import io.entframework.kernel.system.modular.notice.entity.SysNotice;

import java.util.List;

/**
 * 通知服务类
 *
 * @date 2021/1/8 19:56
 */
public interface SysNoticeService extends BaseService<SysNoticeRequest, SysNoticeResponse, SysNotice> {

    /**
     * 添加系统应用
     *
     * @param sysNoticeRequest 添加参数
     * @date 2021/1/9 14:57
     */
    void add(SysNoticeRequest sysNoticeRequest);

    /**
     * 删除系统应用
     *
     * @param sysNoticeRequest 删除参数
     * @date 2021/1/9 14:57
     */
    void del(SysNoticeRequest sysNoticeRequest);

    /**
     * 编辑系统应用
     *
     * @param sysNoticeRequest 编辑参数
     * @date 2021/1/9 14:58
     */
    SysNoticeResponse update(SysNoticeRequest sysNoticeRequest);

    /**
     * 查看系统应用
     *
     * @param sysNoticeRequest 查看参数
     * @return 系统应用
     * @date 2021/1/9 14:56
     */
    SysNoticeResponse detail(SysNoticeRequest sysNoticeRequest);

    /**
     * 查询系统应用
     *
     * @param sysNoticeRequest 查询参数
     * @return 查询分页结果
     * @date 2021/1/9 14:56
     */
    PageResult<SysNoticeResponse> findPage(SysNoticeRequest sysNoticeRequest);

    /**
     * 系统应用列表
     *
     * @param sysNoticeRequest 查询参数
     * @return 系统应用列表
     * @date 2021/1/9 14:56
     */
    List<SysNoticeResponse> findList(SysNoticeRequest sysNoticeRequest);

}
