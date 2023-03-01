/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.system.api.pojo.organization.OrganizationTreeNode;
import io.entframework.kernel.system.api.pojo.request.HrOrganizationRequest;
import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;
import io.entframework.kernel.system.modular.service.HrOrganizationService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统组织机构控制器
 *
 * @date 2020/11/18 21:55
 */
@RestController
@ApiResource(name = "系统组织机构管理")
public class HrOrganizationController {

    @Resource
    private HrOrganizationService hrOrganizationService;

    /**
     * 添加系统组织机构
     *
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "添加系统组织机构", path = "/hr-organization/add")
    public ResponseData<Void> add(
            @RequestBody @Validated(BaseRequest.add.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.add(hrOrganizationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 删除系统组织机构
     *
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "删除系统组织机构", path = "/hr-organization/delete")
    public ResponseData<Void> delete(
            @RequestBody @Validated(BaseRequest.delete.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.del(hrOrganizationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 更新系统组织机构
     *
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "更新系统组织机构", path = "/hr-organization/update")
    public ResponseData<Void> update(
            @RequestBody @Validated(BaseRequest.update.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.update(hrOrganizationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 修改组织机构状态
     *
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "修改组织机构状态", path = "/hr-organization/status")
    public ResponseData<Void> updateStatus(
            @RequestBody @Validated(BaseRequest.updateStatus.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.updateStatus(hrOrganizationRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 查看详情系统组织机构
     *
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "查看详情系统组织机构", path = "/hr-organization/detail")
    public ResponseData<HrOrganizationResponse> detail(
            @Validated(BaseRequest.detail.class) HrOrganizationRequest hrOrganizationRequest) {
        return ResponseData.ok(hrOrganizationService.detail(hrOrganizationRequest));
    }

    /**
     * 分页查询系统组织机构
     *
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "分页查询系统组织机构", path = "/hr-organization/page")
    public ResponseData<PageResult<HrOrganizationResponse>> page(HrOrganizationRequest hrOrganizationRequest) {
        return ResponseData.ok(hrOrganizationService.findPage(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构
     *
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "获取全部系统组织机构-树形", path = "/hr-organization/list")
    public ResponseData<List<HrOrganizationResponse>> list(HrOrganizationRequest hrOrganizationRequest) {
        return ResponseData.ok(hrOrganizationService.findList(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构树（用于新增，编辑组织机构时选择上级节点，用于获取用户管理界面左侧组织机构树）
     *
     * @date 2021/01/05 15:55
     */
    @GetResource(name = "获取全部系统组织机构树", path = "/hr-organization/tree")
    public ResponseData<List<OrganizationTreeNode>> organizationTree(HrOrganizationRequest hrOrganizationRequest) {
        return ResponseData.ok(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

    /**
     * 获取组织机构树（用于用户绑定数据范围，可以渲染是否选中信息）
     *
     * @date 2021/3/19 22:20
     */
    @GetResource(name = "获取组织机构树(用于用户绑定数据范围)", path = "/hr-organization/user-bind-org-scope")
    public ResponseData<List<OrganizationTreeNode>> userBindOrgScope(
            @Validated(HrOrganizationRequest.userBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        return ResponseData.ok(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

}
