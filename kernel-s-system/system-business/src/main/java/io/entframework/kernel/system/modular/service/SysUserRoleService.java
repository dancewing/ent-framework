/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysUserRoleResponse;
import io.entframework.kernel.system.modular.entity.SysUserRole;

import java.util.List;

/**
 * 系统用户角色service接口
 *
 * @date 2021/2/3 15:23
 */
public interface SysUserRoleService extends BaseService<SysUserRoleRequest, SysUserRoleResponse, SysUserRole> {


    /**
     * 新增
     *
     * @param sysUserRoleRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void add(SysUserRoleRequest sysUserRoleRequest);

    /**
     * 删除
     *
     * @param sysUserRoleRequest 参数对象
     * @date 2021/1/26 12:52
     */
    void del(SysUserRoleRequest sysUserRoleRequest);

    /**
     * 根据用户id删除角色
     *
     * @param userId 用户id
     * @date 2021/1/26 12:52
     */
    void delByUserId(Long userId);

    /**
     * 修改
     *
     * @param sysUserRoleRequest 参数对象
     * @date 2021/1/26 12:52
     */
    SysUserRoleResponse update(SysUserRoleRequest sysUserRoleRequest);

    /**
     * 查询-详情
     *
     * @param sysUserRoleRequest 参数对象
     * @date 2021/1/26 12:52
     */
    SysUserRole detail(SysUserRoleRequest sysUserRoleRequest);

    /**
     * 查询-列表
     *
     * @param sysUserRoleRequest 参数对象
     * @date 2021/1/26 12:52
     */
    List<SysUserRole> findList(SysUserRoleRequest sysUserRoleRequest);

    /**
     * 根据userId查询列表
     *
     * @param userId 用户id
     * @return
     * @date 2021/2/3 15:06
     */
    List<SysUserRole> findListByUserId(Long userId);

    /**
     * 根据userId查询角色集合
     *
     * @param userId 用户id
     * @return 用户角色集合
     * @date 2021/2/3 15:09
     */
    List<Long> findRoleIdsByUserId(Long userId);

    /**
     * 角色分配
     *
     * @param sysUserRequest 请求参数
     * @return
     * @date 2021/2/3 15:16
     */
    void assignRoles(SysUserRequest sysUserRequest);

}
