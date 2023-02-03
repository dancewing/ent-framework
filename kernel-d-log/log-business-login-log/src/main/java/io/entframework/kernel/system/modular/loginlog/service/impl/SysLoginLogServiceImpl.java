/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.loginlog.service.impl;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.repository.BaseRepository;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import io.entframework.kernel.log.api.pojo.loginlog.SysLoginLogResponse;
import io.entframework.kernel.system.modular.loginlog.constants.LoginLogConstant;
import io.entframework.kernel.system.modular.loginlog.entity.SysLoginLog;
import io.entframework.kernel.system.modular.loginlog.service.SysLoginLogService;
import io.entframework.kernel.log.api.enums.LoginEventType;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统应用service接口实现类
 *
 * @date 2020/3/13 16:15
 */
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogRequest, SysLoginLogResponse, SysLoginLog> implements SysLoginLogService {

    public SysLoginLogServiceImpl(BaseRepository<SysLoginLog> baseRepository) {
        super(baseRepository, SysLoginLogRequest.class, SysLoginLogResponse.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysLoginLogRequest sysLoginLogRequest) {
        this.deleteBy(sysLoginLogRequest);
    }

    @Override
    public SysLoginLogResponse detail(SysLoginLogRequest sysLoginLogRequest) {
        return this.selectOne(sysLoginLogRequest);
    }

    @Override
    public PageResult<SysLoginLogResponse> findPage(SysLoginLogRequest sysLoginLogRequest) {
        return this.page(sysLoginLogRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysLoginLogRequest sysLoginLog) {
        switch (sysLoginLog.getType()) {
            case LOGIN_IN_SUCCESS:
                sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
                sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_SUCCESS);
                sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_IN_SUCCESS_MESSAGE);
                break;
            case LOGIN_IN_FAIL:
                sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
                sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
                break;
            case LOGIN_OUT_SUCCESS:
                sysLoginLog.setLlgName(LoginLogConstant.LOGIN_OUT_LOGINNAME);
                sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_SUCCESS);
                sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_OUT_SUCCESS_MESSAGE);
                break;
            case LOGIN_OUT_FAIL:
                sysLoginLog.setLlgName(LoginLogConstant.LOGIN_OUT_LOGINNAME);
                sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
                sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_OUT_SUCCESS_FAIL);
                break;
            default:
                sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
                sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
                sysLoginLog.setLlgMessage(LoginLogConstant.OPERATION_FAIL);
        }
        this.insert(sysLoginLog);
        return true;
    }

    @Override
    public void delAll() {
        this.deleteBy(new SysLoginLogRequest());
    }

}
