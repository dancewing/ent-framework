/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.resource.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.api.util.IdWorker;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.resource.api.constants.ResourceConstants;
import io.entframework.kernel.resource.api.pojo.LayuiApiResourceTreeNode;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.resource.api.pojo.SysResourceRequest;
import io.entframework.kernel.resource.api.pojo.SysResourceResponse;
import io.entframework.kernel.resource.modular.entity.SysResource;
import io.entframework.kernel.resource.modular.entity.SysResourceDynamicSqlSupport;
import io.entframework.kernel.resource.modular.factory.ResourceFactory;
import io.entframework.kernel.resource.modular.service.SysResourceService;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.constants.TreeConstants;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceParam;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceUrlParam;
import io.entframework.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ????????? ???????????????
 *
 * @date 2020/11/23 22:45
 */
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceRequest, SysResourceResponse, SysResource>
        implements SysResourceService {

    @Resource(name = ResourceConstants.RESOURCE_CACHE_BEAN_NAME)
    private CacheOperatorApi<ResourceDefinition> resourceCache;

    public SysResourceServiceImpl() {
        super(SysResourceRequest.class, SysResourceResponse.class, SysResource.class);
    }

    @Override
    public PageResult<SysResourceResponse> findPage(SysResourceRequest sysResourceRequest) {
        return this.page(sysResourceRequest);
    }

    @Override
    public List<SysResourceResponse> findList(SysResourceRequest sysResourceRequest) {
        List<SysResourceResponse> menuResourceList = this.select(sysResourceRequest);

        // ?????????????????????????????????
        SysResourceResponse sysResource = new SysResourceResponse();
        sysResource.setResourceCode("");
        sysResource.setResourceName("????????????(???)");
        menuResourceList.add(0, sysResource);

        return menuResourceList;
    }

    @Override
    public List<ResourceTreeNode> getResourceList(List<String> resourceCodes, Set<String> restrictCodes,
            boolean treeBuildFlag) {
        List<ResourceTreeNode> res = new ArrayList<>();

        // ?????????????????????
        List<SysResource> allResource = this.getRepository().select(getEntityClass(), c -> {
            c.where(SysResourceDynamicSqlSupport.requiredPermissionFlag, SqlBuilder.isEqualTo(YesOrNotEnum.Y)).and(
                    SysResourceDynamicSqlSupport.resourceCode,
                    SqlBuilder.isIn(resourceCodes).filter(ObjectUtil::isNotEmpty));
            return c;
        });

        // ?????????????????????????????????
        Map<String, List<SysResource>> modularMap = new HashMap<>();
        for (SysResource sysResource : allResource) {
            List<SysResource> sysResources = modularMap.get(sysResource.getModularName());

            // ?????????????????????
            if (ObjectUtil.isEmpty(sysResources)) {
                sysResources = new ArrayList<>();
                modularMap.put(sysResource.getModularName(), sysResources);
            }
            // ?????????????????????
            sysResources.add(sysResource);
        }

        // ??????????????????
        for (Map.Entry<String, List<SysResource>> entry : modularMap.entrySet()) {
            ResourceTreeNode item = new ResourceTreeNode();
            item.setResourceFlag(false);
            String id = IdWorker.get32UUID();
            item.setCode(id);
            item.setParentCode(RuleConstants.TREE_ROOT_ID.toString());
            item.setNodeName(entry.getKey());

            // ???????????????????????????????????????
            int checkedNumber = 0;

            // ??????????????????
            for (SysResource resource : entry.getValue()) {
                ResourceTreeNode subItem = new ResourceTreeNode();
                // ????????????????????????
                if (!resourceCodes.contains(resource.getResourceCode())) {
                    subItem.setChecked(false);
                }
                else {
                    checkedNumber++;

                    // ??????????????????
                    item.setChecked(true);
                    subItem.setChecked(true);
                }
                subItem.setResourceFlag(true);
                subItem.setNodeName(resource.getResourceName());
                subItem.setCode(resource.getResourceCode());
                subItem.setParentCode(id);
                res.add(subItem);
            }

            // ?????????????????????
            if (checkedNumber == entry.getValue().size()) {
                item.setChecked(true);
                item.setIndeterminate(false);
            }
            else if (checkedNumber == 0) {
                item.setChecked(false);
                item.setIndeterminate(false);
            }
            else {
                item.setChecked(false);
                item.setIndeterminate(true);
            }

            res.add(item);
        }

        // ??????map???????????????
        if (treeBuildFlag) {
            return new DefaultTreeBuildFactory<ResourceTreeNode>().doTreeBuild(res);
        }
        else {
            return res;
        }
    }

    @Override
    public List<LayuiApiResourceTreeNode> getApiResourceTree(SysResourceRequest sysResourceRequest) {

        // 1. ?????????????????????
        SysResourceRequest query = new SysResourceRequest();
        query.setViewFlag(YesOrNotEnum.N);

        List<SysResource> allResource = this.getRepository().select(getEntityClass(), c -> {
            c.where(SysResourceDynamicSqlSupport.viewFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
                    .and(SysResourceDynamicSqlSupport.url, SqlBuilder.isEqualTo(sysResourceRequest.getResourceName()));
            return c;
        });

        // 2. ??????????????????????????????map
        Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = divideResources(allResource);

        // 3. ????????????code?????????name?????????
        Map<String, String> modularCodeName = createModularCodeName(allResource);

        // 4. ??????map???????????????
        return createResourceTree(appModularResources, modularCodeName);
    }

    @Override
    public ResourceDefinition getApiResourceDetail(SysResourceRequest sysResourceRequest) {
        SysResource query = this.converterService.convert(sysResourceRequest, getEntityClass());
        Optional<SysResource> sysResource = this.getRepository().selectOne(query);
        if (sysResource.isPresent()) {

            // ???????????????ResourceDefinition
            ResourceDefinition resourceDefinition = ResourceFactory.createResourceDefinition(sysResource.get());

            // ???????????????????????????
            return ResourceFactory.fillResourceDetail(resourceDefinition);
        }
        else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteResourceByProjectCode(String projectCode) {
        SysResourceRequest request = new SysResourceRequest();
        request.setProjectCode(projectCode);
        this.deleteBy(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysResourcePersistencePojo> reportResourcesAndGetResult(ResourceParam reportResourceReq) {
        String projectCode = reportResourceReq.getProjectCode();
        Map<String, Map<String, ResourceDefinition>> resourceDefinitions = reportResourceReq.getResourceDefinitions();

        if (ObjectUtil.isEmpty(projectCode) || resourceDefinitions == null) {
            return new ArrayList<>();
        }

        // ??????project ?????????????????????????????????
        Map<String, SysResource> resourcesByProject = getResourcesByProjectCode(projectCode);

        // ?????????????????????????????????
        ArrayList<SysResource> allResources = new ArrayList<>();
        ArrayList<SysResource> batchInsertResources = new ArrayList<>();
        ArrayList<ResourceDefinition> resourceDefinitionArrayList = new ArrayList<>();
        for (Map.Entry<String, Map<String, ResourceDefinition>> appModularResources : resourceDefinitions.entrySet()) {
            Map<String, ResourceDefinition> value = appModularResources.getValue();
            for (Map.Entry<String, ResourceDefinition> modularResources : value.entrySet()) {
                resourceDefinitionArrayList.add(modularResources.getValue());
                SysResource resource = ResourceFactory.createResource(modularResources.getValue());
                if (resourcesByProject.containsKey(resource.getResourceCode())) {
                    SysResource sysResource = resourcesByProject.get(resource.getResourceCode());
                    if (!ResourceFactory.equals(sysResource, resource)) {
                        resource.setResourceId(sysResource.getResourceId());
                        resource.setUpdateTime(LocalDateTime.now());
                        resource.setUpdateUser(-1L);
                        resource.setUpdateUserName("system");
                        this.getRepository().updateByPrimaryKey(resource);
                    }
                }
                else {
                    resource.setCreateTime(LocalDateTime.now());
                    resource.setCreateUser(-1L);
                    resource.setCreateUserName("system");
                    batchInsertResources.add(resource);
                }
                allResources.add(resource);
            }
        }
        if (!batchInsertResources.isEmpty()) {
            this.getRepository().insertMultiple(batchInsertResources);
        }

        // ???????????????????????????
        Map<String, ResourceDefinition> resourceDefinitionMap = ResourceFactory
                .orderedResourceDefinition(resourceDefinitionArrayList);
        for (Map.Entry<String, ResourceDefinition> entry : resourceDefinitionMap.entrySet()) {
            resourceCache.put(entry.getKey(), entry.getValue());
        }

        // ??????????????????
        ArrayList<SysResourcePersistencePojo> finalResult = new ArrayList<>();
        for (SysResource item : allResources) {
            SysResourcePersistencePojo sysResourcePersistencePojo = new SysResourcePersistencePojo();
            BeanUtil.copyProperties(item, sysResourcePersistencePojo);
            finalResult.add(sysResourcePersistencePojo);
        }

        return finalResult;
    }

    public Map<String, SysResource> getResourcesByProjectCode(String projectCode) {
        SysResource query = new SysResource();
        query.setProjectCode(projectCode);
        // ??????????????????
        List<SysResource> list = this.getRepository().selectBy(query);
        return list.stream().collect(Collectors.toMap(SysResource::getResourceCode, Function.identity()));
    }

    @Override
    public ResourceDefinition getResourceByUrl(ResourceUrlParam resourceUrlReq) {
        if (ObjectUtil.isEmpty(resourceUrlReq.getUrl())) {
            return null;
        }
        else {

            // ?????????????????????
            ResourceDefinition tempCachedResourceDefinition = resourceCache.get(resourceUrlReq.getUrl());
            if (tempCachedResourceDefinition != null) {
                return tempCachedResourceDefinition;
            }

            SysResource query = new SysResource();
            query.setUrl(resourceUrlReq.getUrl());
            // ?????????????????????????????????
            List<SysResource> resources = this.getRepository().selectBy(query);

            if (resources == null || resources.isEmpty()) {
                return null;
            }
            else {

                // ????????????????????????
                SysResource resource = resources.get(0);
                ResourceDefinition resourceDefinition = new ResourceDefinition();
                BeanUtils.copyProperties(resource, resourceDefinition);

                // ?????????????????????????????????, ????????????????????????????????????????????????true,?????????false
                YesOrNotEnum requiredLoginFlag = resource.getRequiredLoginFlag();
                resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y == requiredLoginFlag);

                // ???????????????????????????????????????????????????????????????????????????true,?????????false
                YesOrNotEnum requiredPermissionFlag = resource.getRequiredPermissionFlag();
                resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y == requiredPermissionFlag);

                // ???????????????????????????
                resourceCache.put(resourceDefinition.getUrl(), resourceDefinition);

                return resourceDefinition;
            }
        }
    }

    @Override
    public Set<String> getResourceUrlsListByCodes(Set<String> resourceCodes) {

        if (resourceCodes == null || resourceCodes.isEmpty()) {
            return new HashSet<>();
        }

        // ??????????????????
        List<SysResource> list = this.getRepository().select(getEntityClass(), c -> {
            c.where(SysResourceDynamicSqlSupport.resourceCode, SqlBuilder.isIn(resourceCodes));
            return c;
        });
        return list.stream().map(SysResource::getUrl).collect(Collectors.toSet());
    }

    @Override
    public long getResourceCount() {
        return this.countBy(new SysResourceRequest());
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     * @return ?????????key???????????????????????????key??????????????????????????????????????????????????????????????????
     * @date 2020/12/18 15:34
     */
    private Map<String, Map<String, List<LayuiApiResourceTreeNode>>> divideResources(List<SysResource> sysResources) {
        HashMap<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = new HashMap<>();
        for (SysResource sysResource : sysResources) {

            // ???????????????????????????
            String appCode = sysResource.getAppCode();
            Map<String, List<LayuiApiResourceTreeNode>> modularResource = appModularResources.get(appCode);

            // ????????????????????????????????????map
            if (modularResource == null) {
                modularResource = new HashMap<>();
            }

            // ??????????????????????????????????????????appModularResources??????
            List<LayuiApiResourceTreeNode> resourceTreeNodes = modularResource.get(sysResource.getModularCode());
            if (resourceTreeNodes == null) {
                resourceTreeNodes = new ArrayList<>();
            }

            // ?????????????????????????????????
            LayuiApiResourceTreeNode resourceTreeNode = new LayuiApiResourceTreeNode();
            resourceTreeNode.setResourceFlag(true);
            resourceTreeNode.setTitle(sysResource.getResourceName());
            resourceTreeNode.setId(sysResource.getResourceCode());
            resourceTreeNode.setParentId(sysResource.getModularCode());
            resourceTreeNode.setUrl(sysResource.getUrl());
            resourceTreeNode.setSpread(false);
            resourceTreeNode.setSlotsValue();
            resourceTreeNodes.add(resourceTreeNode);

            modularResource.put(sysResource.getModularCode(), resourceTreeNodes);
            appModularResources.put(appCode, modularResource);
        }
        return appModularResources;
    }

    /**
     * ????????????code???name?????????
     *
     * @date 2020/12/21 11:23
     */
    private Map<String, String> createModularCodeName(List<SysResource> resources) {
        HashMap<String, String> modularCodeName = new HashMap<>();
        for (SysResource resource : resources) {
            modularCodeName.put(resource.getModularCode(), resource.getModularName());
        }
        return modularCodeName;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @date 2020/12/18 15:45
     */
    private List<LayuiApiResourceTreeNode> createResourceTree(
            Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources,
            Map<String, String> modularCodeName) {

        List<LayuiApiResourceTreeNode> finalTree = new ArrayList<>();

        // ???????????????????????????????????????
        Set<Map.Entry<String, Map<String, List<LayuiApiResourceTreeNode>>>> entries = appModularResources.entrySet();
        for (Map.Entry<String, Map<String, List<LayuiApiResourceTreeNode>>> entry : entries) {
            String appName = entry.getKey();
            // ????????????????????????
            LayuiApiResourceTreeNode appNode = new LayuiApiResourceTreeNode();
            appNode.setId(appName);
            appNode.setTitle(appName);
            appNode.setSpread(true);
            appNode.setResourceFlag(false);
            appNode.setSlotsValue();
            appNode.setParentId(TreeConstants.DEFAULT_PARENT_ID.toString());

            // ????????????????????????????????????
            Map<String, List<LayuiApiResourceTreeNode>> modularResources = entry.getValue();

            // ??????????????????
            ArrayList<LayuiApiResourceTreeNode> modularNodes = new ArrayList<>();
            for (String modularCode : modularResources.keySet()) {
                LayuiApiResourceTreeNode modularNode = new LayuiApiResourceTreeNode();
                modularNode.setId(modularCode);
                modularNode.setTitle(modularCodeName.get(modularCode));
                modularNode.setParentId(appName);
                modularNode.setSpread(false);
                modularNode.setResourceFlag(false);
                modularNode.setChildren(modularResources.get(modularCode));
                modularNode.setSlotsValue();
                modularNodes.add(modularNode);
            }

            // ????????????????????????????????????
            appNode.setChildren(modularNodes);

            // ?????????????????????
            finalTree.add(appNode);
        }

        return finalTree;
    }

}
