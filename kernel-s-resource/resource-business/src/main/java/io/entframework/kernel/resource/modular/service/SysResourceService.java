/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.resource.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.resource.api.pojo.LayuiApiResourceTreeNode;
import io.entframework.kernel.resource.api.pojo.SysResourceRequest;
import io.entframework.kernel.resource.api.pojo.SysResourceResponse;
import io.entframework.kernel.resource.modular.entity.SysResource;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;

import java.util.List;

/**
 * 资源服务类
 *
 * @date 2020/11/24 19:56
 */
public interface SysResourceService
		extends BaseService<SysResourceRequest, SysResourceResponse, SysResource>, ResourceServiceApi {

	/**
	 * 获取资源分页列表
	 * @param sysResourceRequest 请求参数
	 * @return 返回结果
	 * @date 2020/11/24 20:45
	 */
	PageResult<SysResourceResponse> findPage(SysResourceRequest sysResourceRequest);

	/**
	 * 通过应用code获取获取资源下拉列表
	 * <p>
	 * 只获取菜单资源
	 * @param sysResourceRequest 请求参数
	 * @return 响应下拉结果
	 * @date 2020/11/24 20:45
	 */
	List<SysResourceResponse> findList(SysResourceRequest sysResourceRequest);

	/**
	 * 获取资源树列表，用于生成api接口
	 * @return 资源树列表
	 * @date 2020/12/18 15:06
	 */
	List<LayuiApiResourceTreeNode> getApiResourceTree(SysResourceRequest sysResourceRequest);

	/**
	 * 获取资源的详情
	 * @param sysResourceRequest 请求参数
	 * @return 资源详情
	 * @date 2020/12/18 16:04
	 */
	ResourceDefinition getApiResourceDetail(SysResourceRequest sysResourceRequest);

	/**
	 * 删除某个项目的所有资源
	 * @param projectCode 项目编码，一般为spring application name
	 * @date 2020/11/24 20:46
	 */
	void deleteResourceByProjectCode(String projectCode);

}
