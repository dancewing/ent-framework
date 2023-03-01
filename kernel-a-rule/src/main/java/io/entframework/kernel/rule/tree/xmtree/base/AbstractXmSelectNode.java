/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.rule.tree.xmtree.base;

import java.util.List;

/**
 * 封装用于xm-select组件的节点的方法
 *
 * @date 2021/1/31 18:30
 */
public interface AbstractXmSelectNode {

	/**
	 * 显示的名称
	 *
	 * @date 2021/1/31 18:30
	 */
	String getName();

	/**
	 * 选中值, 当前多选唯一
	 *
	 * @date 2021/1/31 18:30
	 */
	String getValue();

	/**
	 * 是否选中
	 *
	 * @date 2021/1/31 18:31
	 */
	Boolean getSelected();

	/**
	 * 是否禁用
	 *
	 * @date 2021/1/31 18:31
	 */
	Boolean getDisabled();

	/**
	 * 获取分组的列表
	 *
	 * @date 2021/1/31 18:33
	 */
	List<?> getChildren();

}
