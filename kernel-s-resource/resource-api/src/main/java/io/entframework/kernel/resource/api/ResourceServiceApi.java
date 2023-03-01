/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.resource.api;

import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.scanner.api.ResourceReportApi;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceUrlParam;

import java.util.List;
import java.util.Set;

/**
 * 资源服务相关接口
 *
 * @date 2020/12/3 15:45
 */
public interface ResourceServiceApi extends ResourceReportApi {

    /**
     * 通过url获取资源
     * @param resourceUrlReq 资源url的封装
     * @return 资源详情
     * @date 2020/10/19 22:06
     */
    ResourceDefinition getResourceByUrl(ResourceUrlParam resourceUrlReq);

    /**
     * 获取资源的url列表，根据资源code集合查询
     * @param resourceCodes 资源编码集合
     * @return 资源url列表
     * @date 2020/11/29 19:49
     */
    Set<String> getResourceUrlsListByCodes(Set<String> resourceCodes);

    /**
     * 获取当前资源url的数量
     *
     * @date 2021/11/3 15:11
     */
    long getResourceCount();

    /**
     * 构建菜单和角色资源树
     * @param resourceCodes
     * @param restrictCodes
     * @param treeBuildFlag
     * @return
     */
    List<ResourceTreeNode> getResourceList(List<String> resourceCodes, Set<String> restrictCodes,
            boolean treeBuildFlag);

}
