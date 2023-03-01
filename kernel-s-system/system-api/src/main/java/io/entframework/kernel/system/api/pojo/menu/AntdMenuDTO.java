/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.api.pojo.menu;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 封装antd vue需要的dto
 *
 * @date 2021/3/23 21:26
 */
@Data
public class AntdMenuDTO {

	/**
	 * 菜单的名称
	 */
	@ChineseDescription("菜单的名称")
	private String name;

	/**
	 * 菜单的图标
	 */
	@ChineseDescription("菜单的图标")
	private String icon;

	/**
	 * 路由地址(要以/开头)，必填
	 */
	@ChineseDescription("路由地址(要以/开头)，必填")
	private String path;

	/**
	 * 为true只注册路由不显示在左侧菜单(比如独立的添加页面)
	 */
	@ChineseDescription("为true只注册路由不显示在左侧菜单(比如独立的添加页面)")
	private Boolean hide;

	/**
	 * 配置选中的path地址，比如修改页面不在侧栏，打开后侧栏就没有选中，这个配置选中地址，非必须
	 */
	@ChineseDescription("配置选中的path地址")
	private String active;

	private MenuMetaDTO meta;

	/**
	 * 子级
	 */
	@ChineseDescription("子级")
	private List<AntdMenuDTO> children;

}
