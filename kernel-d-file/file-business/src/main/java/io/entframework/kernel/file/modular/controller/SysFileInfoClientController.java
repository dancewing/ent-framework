/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.file.modular.controller;

import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.file.modular.service.SysFileInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 文件信息管理 客户端
 *
 * @date 2020/12/27 13:39
 */
@RestController
public class SysFileInfoClientController {

	@Resource
	private SysFileInfoService sysFileInfoService;

	@GetMapping(path = "/client/sys-file/get-file-info-without-content")
	public SysFileInfoResponse getFileInfoWithoutContent(Long fileId) {
		return sysFileInfoService.getFileInfoWithoutContent(fileId);
	}

	@GetMapping(path = "/client/sys-file/get-file-auth-url")
	public String getFileAuthUrl(Long fileId) {
		return sysFileInfoService.getFileAuthUrl(fileId);
	}

	@GetMapping(path = "/client/sys-file/get-file-auth-url-with-token")
	public String getFileAuthUrl(Long fileId, String token) {
		return sysFileInfoService.getFileAuthUrl(fileId, token);
	}

	@GetMapping(path = "/client/sys-file/get-file-un-auth-url")
	public String getFileUnAuthUrl(Long fileId) {
		return sysFileInfoService.getFileUnAuthUrl(fileId);
	}

}
