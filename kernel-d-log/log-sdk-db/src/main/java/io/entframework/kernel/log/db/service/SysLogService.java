/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.db.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.log.api.pojo.manage.SysLogRequest;
import io.entframework.kernel.log.api.pojo.manage.SysLogResponse;
import io.entframework.kernel.log.db.entity.SysLog;

import java.util.List;

/**
 * 日志记录 service接口
 *
 * @date 2020/11/2 17:44
 */
public interface SysLogService extends BaseService<SysLogRequest, SysLogResponse, SysLog> {

    /**
     * 新增
     * @param sysLogRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void add(SysLogRequest sysLogRequest);

    /**
     * 删除
     * @param sysLogRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void del(SysLogRequest sysLogRequest);

    /**
     * 删除所有数据
     * @param sysLogRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void delAll(SysLogRequest sysLogRequest);

    /**
     * 查看日志详情
     *
     * @date 2021/1/11 17:51
     */
    SysLogResponse detail(SysLogRequest logManagerParam);

    /**
     * 查询-列表-按实体对象
     * @param logManagerParam 参数对象
     * @date 2021/1/26 12:52
     */
    List<SysLogResponse> findList(SysLogRequest logManagerParam);

    /**
     * 查询-列表-分页-按实体对象
     * @param logManagerParam 参数对象
     * @date 2021/1/26 12:52
     */
    PageResult<SysLogResponse> findPage(SysLogRequest logManagerParam);

}
