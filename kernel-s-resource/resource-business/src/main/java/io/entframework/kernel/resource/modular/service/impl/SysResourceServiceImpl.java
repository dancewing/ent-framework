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
import io.entframework.kernel.db.mds.repository.BaseRepository;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import io.entframework.kernel.resource.api.constants.ResourceConstants;
import io.entframework.kernel.resource.api.pojo.LayuiApiResourceTreeNode;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.resource.api.pojo.SysResourceRequest;
import io.entframework.kernel.resource.api.pojo.SysResourceResponse;
import io.entframework.kernel.resource.modular.entity.SysResource;
import io.entframework.kernel.resource.modular.factory.ResourceFactory;
import io.entframework.kernel.resource.modular.mapper.SysResourceDynamicSqlSupport;
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
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 资源表 服务实现类
 *
 * @date 2020/11/23 22:45
 */
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceRequest, SysResourceResponse, SysResource> implements SysResourceService {

    @Resource(name = ResourceConstants.RESOURCE_CACHE_BEAN_NAME)
    private CacheOperatorApi<ResourceDefinition> resourceCache;

    public SysResourceServiceImpl(BaseRepository<SysResource> baseRepository) {
        super(baseRepository, SysResourceRequest.class, SysResourceResponse.class);
    }

    @Override
    public PageResult<SysResourceResponse> findPage(SysResourceRequest sysResourceRequest) {
        return this.page(sysResourceRequest);
    }

    @Override
    public List<SysResourceResponse> findList(SysResourceRequest sysResourceRequest) {
        List<SysResourceResponse> menuResourceList = this.select(sysResourceRequest);

        // 增加返回虚拟菜单的情况
        SysResourceResponse sysResource = new SysResourceResponse();
        sysResource.setResourceCode("");
        sysResource.setResourceName("虚拟目录(空)");
        menuResourceList.add(0,  sysResource);

        return menuResourceList;
    }

    @Override
    public List<ResourceTreeNode> getResourceList(List<String> resourceCodes, Set<String> restrictCodes, boolean treeBuildFlag) {
        List<ResourceTreeNode> res = new ArrayList<>();

        // 获取所有的资源
        SelectStatementProvider statement = SqlBuilder
                .select(SysResourceDynamicSqlSupport.selectList)
                .from(SysResourceDynamicSqlSupport.sysResource)
                .where(SysResourceDynamicSqlSupport.requiredPermissionFlag, SqlBuilder.isEqualTo(YesOrNotEnum.Y), // 只查询需要授权的接口
                        SqlBuilder.and(
                                SysResourceDynamicSqlSupport.resourceCode, SqlBuilder.isIn(resourceCodes).filter(ObjectUtil::isNotEmpty)
                        ))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        List<SysResource> allResource = this.getRepository().selectMany(statement);

        // 根据模块名称把资源分类
        Map<String, List<SysResource>> modularMap = new HashMap<>();
        for (SysResource sysResource : allResource) {
            List<SysResource> sysResources = modularMap.get(sysResource.getModularName());

            // 没有就新建一个
            if (ObjectUtil.isEmpty(sysResources)) {
                sysResources = new ArrayList<>();
                modularMap.put(sysResource.getModularName(), sysResources);
            }
            // 把自己加入进去
            sysResources.add(sysResource);
        }

        // 创建一级节点
        for (Map.Entry<String, List<SysResource>> entry : modularMap.entrySet()) {
            ResourceTreeNode item = new ResourceTreeNode();
            item.setResourceFlag(false);
            String id = IdWorker.get32UUID();
            item.setCode(id);
            item.setParentCode(RuleConstants.TREE_ROOT_ID.toString());
            item.setNodeName(entry.getKey());

            // 设置临时变量，统计半开状态
            int checkedNumber = 0;

            //创建二级节点
            for (SysResource resource : entry.getValue()) {
                ResourceTreeNode subItem = new ResourceTreeNode();
                // 判断是否已经拥有
                if (!resourceCodes.contains(resource.getResourceCode())) {
                    subItem.setChecked(false);
                } else {
                    checkedNumber++;

                    // 让父类也选择
                    item.setChecked(true);
                    subItem.setChecked(true);
                }
                subItem.setResourceFlag(true);
                subItem.setNodeName(resource.getResourceName());
                subItem.setCode(resource.getResourceCode());
                subItem.setParentCode(id);
                res.add(subItem);
            }

            // 统计选中的数量
            if (checkedNumber == entry.getValue().size()) {
                item.setChecked(true);
                item.setIndeterminate(false);
            } else if (checkedNumber == 0) {
                item.setChecked(false);
                item.setIndeterminate(false);
            } else {
                item.setChecked(false);
                item.setIndeterminate(true);
            }

            res.add(item);
        }

        // 根据map组装资源树
        if (treeBuildFlag) {
            return new DefaultTreeBuildFactory<ResourceTreeNode>().doTreeBuild(res);
        } else {
            return res;
        }
    }

    @Override
    public List<LayuiApiResourceTreeNode> getApiResourceTree(SysResourceRequest sysResourceRequest) {

        // 1. 获取所有的资源
        SysResourceRequest query = new SysResourceRequest();
        query.setViewFlag(YesOrNotEnum.N);

        SelectStatementProvider statement = SqlBuilder
                .select(SysResourceDynamicSqlSupport.selectList)
                .from(SysResourceDynamicSqlSupport.sysResource)
                .where(SysResourceDynamicSqlSupport.viewFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N),
                        SqlBuilder.and(
                                SysResourceDynamicSqlSupport.url, SqlBuilder.isEqualTo(sysResourceRequest.getResourceName())
                        ))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        List<SysResource> allResource = this.getRepository().selectMany(statement);

        // 2. 按应用和模块编码设置map
        Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = divideResources(allResource);

        // 3. 创建模块code和模块name的映射
        Map<String, String> modularCodeName = createModularCodeName(allResource);

        // 4. 根据map组装资源树
        return createResourceTree(appModularResources, modularCodeName);
    }

    @Override
    public ResourceDefinition getApiResourceDetail(SysResourceRequest sysResourceRequest) {
        SysResource query = this.converterService.convert(sysResourceRequest, getEntityClass());
        Optional<SysResource> sysResource = this.getRepository().selectOne(query);
        if (sysResource.isPresent()) {

            // 实体转化为ResourceDefinition
            ResourceDefinition resourceDefinition = ResourceFactory.createResourceDefinition(sysResource.get());

            // 填充具体的提示信息
            return ResourceFactory.fillResourceDetail(resourceDefinition);
        } else {
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

        //根据project 查找该项目下的所有资源
        Map<String, SysResource> resourcesByProject = getResourcesByProjectCode(projectCode);

        //获取当前应用的所有资源
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
                } else {
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

        //将资源存入缓存一份
        Map<String, ResourceDefinition> resourceDefinitionMap = ResourceFactory.orderedResourceDefinition(resourceDefinitionArrayList);
        for (Map.Entry<String, ResourceDefinition> entry : resourceDefinitionMap.entrySet()) {
            resourceCache.put(entry.getKey(), entry.getValue());
        }

        // 组装返回结果
        ArrayList<SysResourcePersistencePojo> finalResult = new ArrayList<>();
        for (SysResource item : allResources) {
            SysResourcePersistencePojo sysResourcePersistencePojo = new SysResourcePersistencePojo();
            BeanUtil.copyProperties(item, sysResourcePersistencePojo);
            finalResult.add(sysResourcePersistencePojo);
        }

        return finalResult;
    }

    public Map<String, SysResource> getResourcesByProjectCode(String projectCode) {
        // 拼接in条件
        SelectStatementProvider statement = SqlBuilder
                .select(SysResourceDynamicSqlSupport.selectList)
                .from(SysResourceDynamicSqlSupport.sysResource)
                .where(SysResourceDynamicSqlSupport.projectCode, SqlBuilder.isEqualTo(projectCode))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        // 获取资源详情
        List<SysResource> list = this.getRepository().selectMany(statement);
        return list.stream().collect(Collectors.toMap(SysResource::getResourceCode, Function.identity()));
    }

    @Override
    public ResourceDefinition getResourceByUrl(ResourceUrlParam resourceUrlReq) {
        if (ObjectUtil.isEmpty(resourceUrlReq.getUrl())) {
            return null;
        } else {

            // 先从缓存中查询
            ResourceDefinition tempCachedResourceDefinition = resourceCache.get(resourceUrlReq.getUrl());
            if (tempCachedResourceDefinition != null) {
                return tempCachedResourceDefinition;
            }

            SysResource query = new SysResource();
            query.setUrl(resourceUrlReq.getUrl());
            // 缓存中没有去数据库查询
            List<SysResource> resources = this.getRepository().selectBy(query);

            if (resources == null || resources.isEmpty()) {
                return null;
            } else {

                // 获取接口资源信息
                SysResource resource = resources.get(0);
                ResourceDefinition resourceDefinition = new ResourceDefinition();
                BeanUtils.copyProperties(resource, resourceDefinition);

                // 获取是否需要登录的标记, 判断是否需要登录，如果是则设置为true,否则为false
                YesOrNotEnum requiredLoginFlag = resource.getRequiredLoginFlag();
                resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y == requiredLoginFlag);

                // 获取请求权限的标记，判断是否有权限，如果有则设置为true,否则为false
                YesOrNotEnum requiredPermissionFlag = resource.getRequiredPermissionFlag();
                resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y == requiredPermissionFlag);

                // 查询结果添加到缓存
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

        // 拼接in条件
        SelectStatementProvider statement = SqlBuilder
                .select(SysResourceDynamicSqlSupport.url)
                .from(SysResourceDynamicSqlSupport.sysResource)
                .where(SysResourceDynamicSqlSupport.resourceCode, SqlBuilder.isIn(resourceCodes))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        // 获取资源详情
        List<SysResource> list = this.getRepository().selectMany(statement);
        return list.stream().map(SysResource::getUrl).collect(Collectors.toSet());
    }

    @Override
    public long getResourceCount() {
        return this.countBy(new SysResourceRequest());
    }

    /**
     * 划分数据库中的资源，切分成应用和模块分类的集合
     *
     * @return 第一个key是应用名称，第二个key是模块名称，值是应用对应的模块对应的资源列表
     * @date 2020/12/18 15:34
     */
    private Map<String, Map<String, List<LayuiApiResourceTreeNode>>> divideResources(List<SysResource> sysResources) {
        HashMap<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = new HashMap<>();
        for (SysResource sysResource : sysResources) {

            // 查询应用下有无资源
            String appCode = sysResource.getAppCode();
            Map<String, List<LayuiApiResourceTreeNode>> modularResource = appModularResources.get(appCode);

            // 该应用下没资源就创建一个map
            if (modularResource == null) {
                modularResource = new HashMap<>();
            }

            // 查询当前资源的模块，有没有在appModularResources存在
            List<LayuiApiResourceTreeNode> resourceTreeNodes = modularResource.get(sysResource.getModularCode());
            if (resourceTreeNodes == null) {
                resourceTreeNodes = new ArrayList<>();
            }

            // 将当前资源放入资源集合
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
     * 创建模块code和name的映射
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
     * 根据归好类的资源，创建资源树
     *
     * @date 2020/12/18 15:45
     */
    private List<LayuiApiResourceTreeNode> createResourceTree(Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources, Map<String, String> modularCodeName) {

        List<LayuiApiResourceTreeNode> finalTree = new ArrayList<>();

        // 按应用遍历应用模块资源集合
        Set<Map.Entry<String, Map<String, List<LayuiApiResourceTreeNode>>>> entries = appModularResources.entrySet();
        for (Map.Entry<String, Map<String, List<LayuiApiResourceTreeNode>>> entry : entries) {
            String appName = entry.getKey();
            // 创建当前应用节点
            LayuiApiResourceTreeNode appNode = new LayuiApiResourceTreeNode();
            appNode.setId(appName);
            appNode.setTitle(appName);
            appNode.setSpread(true);
            appNode.setResourceFlag(false);
            appNode.setSlotsValue();
            appNode.setParentId(TreeConstants.DEFAULT_PARENT_ID.toString());

            // 遍历当前应用下的模块资源
            Map<String, List<LayuiApiResourceTreeNode>> modularResources = entry.getValue();

            // 创建模块节点
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

            // 当前应用下添加模块的资源
            appNode.setChildren(modularNodes);

            // 添加到最终结果
            finalTree.add(appNode);
        }

        return finalTree;
    }

}
