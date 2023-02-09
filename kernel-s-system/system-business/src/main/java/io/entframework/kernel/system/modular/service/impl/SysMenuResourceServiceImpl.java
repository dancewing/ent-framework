/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.system.api.pojo.request.SysMenuResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysMenuResourceResponse;
import io.entframework.kernel.system.modular.entity.SysMenuResource;
import io.entframework.kernel.system.modular.entity.SysMenuResourceDynamicSqlSupport;
import io.entframework.kernel.system.modular.service.SysMenuResourceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class SysMenuResourceServiceImpl extends BaseServiceImpl<SysMenuResourceRequest, SysMenuResourceResponse, SysMenuResource> implements SysMenuResourceService {
    public SysMenuResourceServiceImpl() {
        super(SysMenuResourceRequest.class, SysMenuResourceResponse.class, SysMenuResource.class);
    }

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Override
    public List<ResourceTreeNode> getMenuResourceTree(Long menuId) {
        List<SysMenuResource> list = this.getRepository().select(getEntityClass(), c -> c.where(SysMenuResourceDynamicSqlSupport.menuId, SqlBuilder.isEqualTo(menuId)));
        List<String> resourceCodes = list.stream().map(SysMenuResource::getResourceCode).toList();
        return resourceServiceApi.getResourceList(resourceCodes, Collections.emptySet(), true);
    }

    @Override
    public List<String> getMenuResourceCodes(Long menuId) {
        List<SysMenuResource> list = this.getRepository().select(getEntityClass(), c -> c.where(SysMenuResourceDynamicSqlSupport.menuId, SqlBuilder.isEqualTo(menuId)));
        return list.stream().map(SysMenuResource::getResourceCode).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenuResourceBind(SysMenuResourceRequest sysMenuResourceRequest) {
        // 先将所有资源删除掉
        this.getRepository().delete(getEntityClass(), c -> c.where(SysMenuResourceDynamicSqlSupport.menuId, SqlBuilder.isEqualTo(sysMenuResourceRequest.getMenuId())));

        // 需要绑定的资源添加上
        List<String> selectedResource = sysMenuResourceRequest.getSelectedResource();
        if (ObjectUtil.isNotEmpty(selectedResource)) {
            ArrayList<SysMenuResource> menuResources = new ArrayList<>();
            for (String resourceCode : selectedResource) {
                if (resourceCode.contains("$")) {
                    SysMenuResource sysMenuResource = new SysMenuResource();
                    sysMenuResource.setMenuId(sysMenuResourceRequest.getMenuId());
                    sysMenuResource.setResourceCode(resourceCode);
                    menuResources.add(sysMenuResource);
                }
            }
            menuResources.forEach(this.getRepository()::insert);
        }
    }

    @Override
    public List<String> getResourceCodesByBusinessId(List<Long> businessIds) {
        if (ObjectUtil.isEmpty(businessIds)) {
            return new ArrayList<>();
        }

        List<SysMenuResource> list = this.getRepository().select(getEntityClass(), c -> c.where(SysMenuResourceDynamicSqlSupport.menuId, SqlBuilder.isIn(businessIds)));

        return list.stream().map(SysMenuResource::getResourceCode).toList();
    }
}