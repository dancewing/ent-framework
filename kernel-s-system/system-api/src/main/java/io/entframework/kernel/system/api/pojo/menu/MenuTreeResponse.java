/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.pojo.menu;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 新版的角色绑定菜单和按钮，用在角色分配菜单和按钮节点
 *
 * @date 2021/8/10 22:36
 */
@Data
public class MenuTreeResponse implements AbstractTreeNode<MenuTreeResponse> {

	/**
	 * 节点ID，可以是菜单id和按钮id
	 */
	@ChineseDescription("节点ID")
	private Long id;

	/**
	 * 节点父ID
	 */
	@ChineseDescription("节点父ID")
	private Long pid;

	/**
	 * 节点名称
	 */
	@ChineseDescription("节点名称")
	private String name;

	/**
	 * code
	 */
	@ChineseDescription("code")
	private String code;

	/**
	 * 是否选择(已拥有的是true)
	 */
	@ChineseDescription("是否选择(已拥有的是true)")
	private Boolean checked;

	/**
	 * 子节点集合
	 */
	@ChineseDescription("子节点集合")
	private List<MenuTreeResponse> children;

	@Override
	public String getNodeId() {
		if (this.id != null) {
			return this.id.toString();
		}
		else {
			return "";
		}
	}

	@Override
	public String getNodeParentId() {
		if (this.pid != null) {
			return this.pid.toString();
		}
		else {
			return "";
		}
	}

	@Override
	public void setChildrenNodes(List<MenuTreeResponse> childrenNodes) {
		this.children = childrenNodes;
	}

}
