/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.response;

import java.math.BigDecimal;
import java.util.List;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.tree.factory.base.AbstractTreeNode;
import io.entframework.kernel.system.api.enums.LinkOpenTypeEnum;
import io.entframework.kernel.system.api.enums.MenuTypeEnum;

import lombok.Data;

@Data
public class SysMenuResponse implements AbstractTreeNode<SysMenuResponse> {

	@ChineseDescription("主键")
	private Long menuId;

	/**
	 * 父id，顶级节点的父id是-1
	 */
	@ChineseDescription("父id，顶级节点的父id是-1")
	private Long menuParentId;

	/**
	 * 父id集合，中括号包住，逗号分隔
	 */
	@ChineseDescription("父id集合，中括号包住，逗号分隔")
	private String menuPids;

	/**
	 * 菜单的名称
	 */
	@ChineseDescription("菜单的名称")
	private String menuName;

	/**
	 * 菜单的类型, 0 目录，1 路由菜单，2 外部链接, 3 按钮
	 */
	@ChineseDescription("菜单类型")
	private MenuTypeEnum menuType;

	/**
	 * 菜单的编码
	 */
	@ChineseDescription("菜单的编码")
	private String menuCode;

	/**
	 * 所属应用
	 */
	@ChineseDescription("所属应用")
	private Long appId;

	/**
	 * 排序
	 */
	@ChineseDescription("排序")
	private BigDecimal menuSort;

	/**
	 * 状态：1-启用，2-禁用
	 */
	@ChineseDescription("状态：1-启用，2-禁用")
	private StatusEnum statusFlag;

	/**
	 * 备注
	 */
	@ChineseDescription("备注")
	private String remark;

	/**
	 * 路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本
	 */
	@ChineseDescription("路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本")
	private String router;

	/**
	 * 图标，适用于antd vue版本
	 */
	@ChineseDescription("图标，适用于antd vue版本")
	private String icon;

	/**
	 * 外部链接打开方式：1-内置打开外链，2-新页面外链，适用于antd vue版本
	 */
	@ChineseDescription("外部链接打开方式：1-内置打开外链，2-新页面外链，适用于antd vue版本")
	private LinkOpenTypeEnum linkOpenType;

	/**
	 * 外部链接地址
	 */
	@ChineseDescription("外部链接地址")
	private String linkUrl;

	/**
	 * 用于非菜单显示页面的重定向url设置
	 */
	@ChineseDescription("用于非菜单显示页面的重定向url设置")
	private String activeUrl;

	/**
	 * 是否可见(分离版用)：Y-是，N-否
	 */
	@ChineseDescription("是否可见")
	private YesOrNotEnum visible;

	@ChineseDescription("应用")
	private SysAppResponse app;

	@ChineseDescription("是否是叶子节点菜单")
	private Boolean leafFlag;

	@ChineseDescription("子节点（表中不存在，用于构造树）")
	private List<SysMenuResponse> children;

	private String menuParentName;

	@Override
	public String getNodeId() {
		if (menuId == null) {
			return null;
		}
		return menuId.toString();
	}

	@Override
	public String getNodeParentId() {
		if (menuParentId == null) {
			return null;
		}
		return menuParentId.toString();
	}

	@Override
	public void setChildrenNodes(List<SysMenuResponse> childrenNodes) {
		this.children = childrenNodes;
	}
}
