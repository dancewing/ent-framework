/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.response;

import java.math.BigDecimal;
import java.util.List;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.tree.factory.base.AbstractTreeNode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统组织机构表
 *
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HrOrganizationResponse extends BaseResponse implements AbstractTreeNode<HrOrganizationResponse> {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long orgId;

    /**
     * 父id，一级节点父id是0
     */
    @ChineseDescription("父id，一级节点父id是0")
    private Long orgParentId;

    /**
     * 父ids
     */
    @ChineseDescription("父ids")
    private String orgPids;

    /**
     * 组织名称
     */
    @ChineseDescription("组织名称")
    private String orgName;

    /**
     * 组织编码
     */
    @ChineseDescription("组织编码")
    private String orgCode;

    /**
     * 排序
     */
    @ChineseDescription("排序")
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @ChineseDescription("状态：1-启用，2-禁用")
    private StatusEnum statusFlag;

    /**
     * 组织机构描述
     */
    @ChineseDescription("组织机构描述")
    private String orgRemark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @ChineseDescription("删除标记（Y-已删除，N-未删除）")
    private String delFlag;

    /**
     * 所有下属组织
     */
    private List<HrOrganizationResponse> children;

    @Override
    public String getNodeId() {
        if (this.orgId == null) {
            return null;
        }
        return this.orgId.toString();
    }

    @Override
    public String getNodeParentId() {
        if (this.orgParentId == null) {
            return null;
        }
        return this.orgParentId.toString();
    }

    @Override
    public void setChildrenNodes(List<HrOrganizationResponse> childrenNodes) {
        this.children = childrenNodes;
    }

}
