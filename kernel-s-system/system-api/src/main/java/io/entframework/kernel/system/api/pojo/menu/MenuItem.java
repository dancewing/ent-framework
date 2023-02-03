/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.pojo.menu;

import io.entframework.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 用于antdv的菜单响应
 *
 * @date 2021/1/7 18:09
 */
@Data
public class MenuItem implements AbstractTreeNode<MenuItem> {

    /**
     * 主键
     */
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    private Long menuParentId;

    /**
     * 路由信息
     */
    private String router;

    /**
     * 图标信息
     */
    private String icon;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 是否隐藏
     */
    private Boolean invisible;

    /**
     * 路由信息
     */
    private MenuAuthorityItem authority;

    /**
     * 子菜单集合
     */
    private List<MenuItem> children;

    @Override
    public String getNodeId() {
        return menuId.toString();
    }

    @Override
    public String getNodeParentId() {
        return menuParentId.toString();
    }

    @Override
    public void setChildrenNodes(List<MenuItem> childrenNodes) {
        this.children = childrenNodes;
    }

}
