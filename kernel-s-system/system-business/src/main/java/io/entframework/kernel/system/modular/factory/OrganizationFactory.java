/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.factory;

import io.entframework.kernel.system.api.pojo.organization.OrganizationTreeNode;
import io.entframework.kernel.system.modular.entity.HrOrganization;

/**
 * 组织机构实体转化
 *
 * @date 2021/1/6 21:03
 */
public class OrganizationFactory {

    /**
     * 实体转换
     *
     * @param hrOrganization 机构信息
     * @return LayuiOrganizationTreeNode layui树实体对象
     * @date 2021/1/5 21:07
     */
    public static OrganizationTreeNode parseOrganizationTreeNode(HrOrganization hrOrganization) {
        OrganizationTreeNode treeNode = new OrganizationTreeNode();
        treeNode.setId(hrOrganization.getOrgId());
        treeNode.setParentId(hrOrganization.getOrgParentId());
        treeNode.setTitle(hrOrganization.getOrgName());
        return treeNode;
    }

}
