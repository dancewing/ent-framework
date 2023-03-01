/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.loginlog.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.log.api.LoginLogServiceApi;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogResponse;
import io.entframework.kernel.system.modular.loginlog.entity.SysLoginLog;

/**
 * 登录日志service接口
 *
 * @date 2021/1/13 10:56
 */
public interface SysLoginLogService
        extends BaseService<SysLoginLogRequest, SysLoginLogResponse, SysLoginLog>, LoginLogServiceApi {

    /**
     * 添加登录日志
     * @param sysLoginLogRequest 参数
     * @date 2021/1/13 10:56
     */
    boolean add(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 删除
     * @param sysLoginLogRequest 参数
     * @date 2021/1/13 10:55
     */
    void del(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 清空登录日志
     *
     * @date 2021/1/13 10:55
     */
    void delAll();

    /**
     * 查看相信
     * @param sysLoginLogRequest 参数
     * @date 2021/1/13 10:56
     */
    SysLoginLogResponse detail(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 分页查询
     * @param sysLoginLogRequest 参数
     * @date 2021/1/13 10:57
     */
    PageResult<SysLoginLogResponse> findPage(SysLoginLogRequest sysLoginLogRequest);

}
