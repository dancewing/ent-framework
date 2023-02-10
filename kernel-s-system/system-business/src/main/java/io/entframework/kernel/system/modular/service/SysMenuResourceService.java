/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.system.api.pojo.request.SysMenuResourceRequest;
import io.entframework.kernel.system.api.pojo.response.SysMenuResourceResponse;
import io.entframework.kernel.system.modular.entity.SysMenuResource;

import java.util.List;

public interface SysMenuResourceService extends BaseService<SysMenuResourceRequest, SysMenuResourceResponse, SysMenuResource> {

    /**
     * 获取资源树
     *
     * @param menuId 菜单id
     * @return 资源树列表
     * @date 2021/8/8 21:56
     */
    List<ResourceTreeNode> getMenuResourceTree(Long menuId);

    /**
     * 获取菜单或菜单按钮绑定资源的树
     *
     * @param menuId
     * @return
     */
    List<String> getMenuResourceCodes(Long menuId);

    /**
     * 添加菜单和资源的绑定
     *
     * @date 2021/8/10 13:58
     */
    void addMenuResourceBind(SysMenuResourceRequest sysMenuResourceRequest);

    List<String> getResourceCodesByBusinessId(List<Long> businessIds);
}