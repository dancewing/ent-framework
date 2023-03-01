/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.resource.api.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于渲染api资源树（layui插件）
 *
 * @date 2021/1/14 21:51
 */
@Data
public class LayuiApiResourceTreeNode implements AbstractTreeNode<LayuiApiResourceTreeNode> {

	/**
	 * 资源的上级编码
	 */
	@ChineseDescription("资源的上级编码")
	private String parentId;

	/**
	 * 节点名称
	 */
	@ChineseDescription("节点名称")
	private String title;

	/**
	 * 资源的编码
	 */
	@ChineseDescription("资源的编码")
	private String id;

	/**
	 * 是否展开状态 不展开-false 展开-true
	 */
	@ChineseDescription("是否展开状态 不展开-false 展开-true")
	private Boolean spread = false;

	/**
	 * 是否是资源标识
	 * <p>
	 * true-是资源标识 false-虚拟节点，不是一个具体资源
	 */
	@ChineseDescription("是否是资源标识")
	private Boolean resourceFlag;

	/**
	 * 节点URL
	 */
	@ChineseDescription("节点URL")
	private String url;

	/**
	 * 图标
	 */
	@ChineseDescription("图标")
	private Map<String, String> slots = new HashMap<>();

	/**
	 * 子节点的集合
	 */
	@ChineseDescription("子节点的集合")
	private List<LayuiApiResourceTreeNode> children;

	public void setSlotsValue() {
		if (resourceFlag) {
			slots.put("icon", "resource");
		}
	}

	@Override
	public String getNodeId() {
		return this.id;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}

	@Override
	public void setChildrenNodes(List<LayuiApiResourceTreeNode> childrenNodes) {
		this.children = childrenNodes;
	}

}
