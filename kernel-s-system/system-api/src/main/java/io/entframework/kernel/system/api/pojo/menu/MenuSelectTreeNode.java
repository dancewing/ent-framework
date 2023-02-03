/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.pojo.menu;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单树节点，用在新增和修改菜单，下拉选父级时候
 *
 * @date 2020/4/5 12:03
 */
@Data
public class MenuSelectTreeNode implements AbstractTreeNode<MenuSelectTreeNode> {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long id;

    /**
     * 父id
     */
    @ChineseDescription("父id")
    private Long parentId;

    /**
     * 名称
     */
    @ChineseDescription("名称")
    private String title;

    /**
     * 值
     */
    @ChineseDescription("值")
    private String value;

    /**
     * 排序，越小优先级越高
     */
    @ChineseDescription("排序，越小优先级越高")
    private BigDecimal weight;

    /**
     * 子节点
     */
    @ChineseDescription("子节点")
    private List<MenuSelectTreeNode> children;

    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.parentId.toString();
    }

    @Override
    public void setChildrenNodes(List<MenuSelectTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }
}
