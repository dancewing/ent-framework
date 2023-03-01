/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleResourceResponse;
import io.entframework.kernel.system.modular.entity.SysRoleResource;
import io.entframework.kernel.system.modular.entity.SysRoleResourceDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysRoleResourceService;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色菜单service接口实现类
 *
 * @date 2020/11/5 上午11:32
 */
public class SysRoleResourceServiceImpl
        extends BaseServiceImpl<SysRoleResourceRequest, SysRoleResourceResponse, SysRoleResource>
        implements SysRoleResourceService {

    @Resource(name = "roleResourceCacheApi")
    private CacheOperatorApi<List<String>> roleResourceCacheApi;

    public SysRoleResourceServiceImpl() {
        super(SysRoleResourceRequest.class, SysRoleResourceResponse.class, SysRoleResource.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantResource(SysRoleRequest sysRoleRequest) {

        Long roleId = sysRoleRequest.getRoleId();

        // 删除所拥有角色关联的资源
        this.getRepository().delete(getEntityClass(),
                c -> c.where(SysRoleResourceDynamicSqlSupport.roleId, SqlBuilder.isEqualTo(roleId)));

        // 清除角色缓存资源
        roleResourceCacheApi.remove(String.valueOf(roleId));

        // 授权资源
        List<String> grantResourceList = sysRoleRequest.getGrantResourceList();
        ArrayList<SysRoleResource> sysRoleResources = new ArrayList<>();

        // 批量保存角色授权资源
        for (String resourceId : grantResourceList) {
            SysRoleResource sysRoleMenu = new SysRoleResource();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setResourceCode(resourceId);
            sysRoleResources.add(sysRoleMenu);
        }
        this.getRepository().insertMultiple(sysRoleResources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleResourceListByRoleId(Long roleId) {
        this.getRepository().delete(getEntityClass(),
                c -> c.where(SysRoleResourceDynamicSqlSupport.roleId, SqlBuilder.isEqualTo(roleId)));

        // 清除角色绑定的资源缓存
        roleResourceCacheApi.remove(String.valueOf(roleId));

    }

    @Override
    public List<String> getRoleResourceCodes(List<Long> roleIdList) {
        return this.getRepository()
                .select(getEntityClass(),
                        c -> c.where(SysRoleResourceDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIdList)))
                .stream().map(SysRoleResource::getResourceCode).toList();
    }

    public List<SysRoleResourceResponse> getRoleResources(List<Long> roleIdList) {
        return this.getRepository()
                .select(getEntityClass(),
                        c -> c.where(SysRoleResourceDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIdList)))
                .stream().map(record -> this.converterService.convert(record, getResponseClass())).toList();
    }

}
