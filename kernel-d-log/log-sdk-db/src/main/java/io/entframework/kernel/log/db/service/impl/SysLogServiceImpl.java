/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.db.service.impl;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.log.api.pojo.manage.SysLogRequest;
import io.entframework.kernel.log.api.pojo.manage.SysLogResponse;
import io.entframework.kernel.log.db.entity.SysLog;
import io.entframework.kernel.log.db.service.SysLogService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 日志记录 service接口实现类
 *
 * @date 2020/11/2 17:45
 */
public class SysLogServiceImpl extends BaseServiceImpl<SysLogRequest, SysLogResponse, SysLog> implements SysLogService {


    public SysLogServiceImpl() {
        super(SysLogRequest.class, SysLogResponse.class, SysLog.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysLogRequest sysLogRequest) {
        this.insert(sysLogRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysLogRequest sysLogRequest) {
        this.deleteBy(sysLogRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAll(SysLogRequest sysLogRequest) {
        this.batchDelete(sysLogRequest);
    }

    @Override
    public SysLogResponse detail(SysLogRequest sysLogRequest) {
        return this.selectOne(sysLogRequest);
    }

    @Override
    public List<SysLogResponse> findList(SysLogRequest sysLogRequest) {
        return this.select(sysLogRequest);
    }

    @Override
    public PageResult<SysLogResponse> findPage(SysLogRequest sysLogRequest) {
        return this.page(sysLogRequest);
    }

}
