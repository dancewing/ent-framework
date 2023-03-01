/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.pojo.request.SysUserDataScopeRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserDataScopeResponse;
import io.entframework.kernel.system.modular.entity.SysUserDataScope;

import java.util.Collection;
import java.util.List;

/**
 * 系统用户数据范围service接口
 *
 * @date 2020/11/6 10:28
 */
public interface SysUserDataScopeService
        extends BaseService<SysUserDataScopeRequest, SysUserDataScopeResponse, SysUserDataScope> {

    /**
     * 新增
     * @param sysUserDataScopeRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void add(SysUserDataScopeRequest sysUserDataScopeRequest);

    /**
     * 删除
     * @param sysUserDataScopeRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void del(SysUserDataScopeRequest sysUserDataScopeRequest);

    /**
     * 根据 用户id 删除
     * @param userId 用户id
     * @date 2021/1/26 12:52
     */
    void delByUserId(Long userId);

    /**
     * 修改
     * @param sysUserDataScopeRequest 参数对象
     * @date 2021/1/26 12:52
     */
    SysUserDataScopeResponse update(SysUserDataScopeRequest sysUserDataScopeRequest);

    /**
     * 查询-详情-根据主键id
     * @param sysUserDataScopeRequest 参数对象
     * @date 2021/1/26 12:52
     */
    SysUserDataScope detail(SysUserDataScopeRequest sysUserDataScopeRequest);

    /**
     * 获取用户的数据范围id集合
     * @param uerId 用户id
     * @return 数据范围id集合
     * @date 2020/11/6 15:01
     */
    List<Long> findOrgIdsByUserId(Long uerId);

    /**
     * 查询-列表-按实体对象
     * @param sysUserDataScopeRequest 参数对象
     * @date 2021/1/26 12:52
     */
    List<SysUserDataScope> findList(SysUserDataScopeRequest sysUserDataScopeRequest);

    /**
     * 分配数据范围
     *
     * @date 2021/2/3 15:49
     */
    void assignData(SysUserRequest sysUserRequest);

    int delete(Collection<Long> organizationIds);

}
