/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.prometheus.mapper;

/**
 * 关闭或者打开prometheus菜单接口
 *
 * @date 2021/3/3 16:12
 */
public interface PrometheusMenuMapper {

	/***
	 * 功能描述: 关闭或者打开prometheus菜单 创建时间: 2021/3/3 16:12
	 *
	 */
	/**
	 * 关闭或者打开prometheus菜单
	 * @param statusFlag 1启用2关闭
	 * @date 2021/3/3 16:12
	 */
	void displayOrClosePrometheusMenu(int statusFlag);

}
