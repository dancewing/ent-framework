/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.file.modular.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.file.api.pojo.request.SysFileStorageRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileStorageResponse;
import io.entframework.kernel.file.modular.entity.SysFileStorage;

/**
 * 文件存储信息 服务类
 *
 * @date 2022/01/08 15:53
 */
public interface SysFileStorageService extends BaseService<SysFileStorageRequest, SysFileStorageResponse, SysFileStorage> {

	/**
	 * 将文件存储在库中
	 *
	 * @date 2022/1/8 16:08
	 */
	void saveFile(Long fileId, byte[] bytes);

	/**
	 * 获取文件的访问url
	 *
	 * @param fileId 文件id
	 * @date 2022/1/8 16:12
	 */
	String getFileAuthUrl(String fileId, String token);

	/**
	 * 获取文件不带鉴权的访问url
	 *
	 * @param fileId 文件id
	 * @date 2022/1/8 16:12
	 */
	String getFileUnAuthUrl(String fileId);

}
