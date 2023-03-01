/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.system.api.OrganizationServiceApi;
import io.entframework.kernel.system.api.pojo.organization.OrganizationTreeNode;
import io.entframework.kernel.system.api.pojo.request.HrOrganizationRequest;
import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;
import io.entframework.kernel.system.modular.entity.HrOrganization;

import java.util.List;
import java.util.Set;

/**
 * 系统组织机构服务
 *
 * @date 2020/11/04 11:05
 */
public interface HrOrganizationService
        extends BaseService<HrOrganizationRequest, HrOrganizationResponse, HrOrganization>, OrganizationServiceApi {

    /**
     * 添加系统组织机构
     * @param hrOrganizationRequest 组织机构请求参数
     * @date 2020/11/04 11:05
     */
    void add(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 删除系统组织机构
     * @param hrOrganizationRequest 组织机构请求参数
     * @date 2020/11/04 11:05
     */
    void del(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 编辑系统组织机构
     * @param hrOrganizationRequest 组织机构请求参数
     * @date 2020/11/04 11:05
     */
    HrOrganizationResponse update(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 修改组织机构状态
     * @param hrOrganizationRequest 请求参数
     * @date 2020/11/18 22:38
     */
    void updateStatus(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查看详情系统组织机构
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情
     * @date 2020/11/04 11:05
     */
    HrOrganizationResponse detail(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 分页查询系统组织机构
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情分页列表
     * @date 2020/11/04 11:05
     */
    PageResult<HrOrganizationResponse> findPage(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询所有系统组织机构
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情列表
     * @date 2020/11/04 11:05
     */
    List<HrOrganizationResponse> findList(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 获取全部系统组织机构树（用于新增，编辑组织机构时选择上级节点，用于获取用户管理界面左侧组织机构树）
     * @param hrOrganizationRequest 查询参数
     * @return 系统组织机构树
     * @date 2020/11/6 13:41
     */
    List<OrganizationTreeNode> organizationTree(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询所有参数组织架构id集合的所有层级的父id，包含父级的父级等
     * @param organizationIds 组织架构id集合
     * @return 被查询参数id集合的所有层级父级id，包含他们本身
     * @date 2020/11/6 14:24
     */
    Set<Long> findAllLevelParentIdsByOrganizations(Set<Long> organizationIds);

}
