/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api;

import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取文件信息的api，及文件操作API
 *
 * @date 2020/11/29 16:21
 */
public interface FileInfoClientApi {

	/**
	 * 获取文件详情
	 * @param fileId 文件id，在文件信息表的id
	 * @return 文件的信息，不包含文件本身的字节信息
	 * @date 2020/11/29 16:26
	 */
	@GetMapping(path = "/client/sys-file/get-file-info-without-content")
	SysFileInfoResponse getFileInfoWithoutContent(@RequestParam("fileId") Long fileId);

	/**
	 * 获取文件的下载地址（带鉴权的），生成外网地址
	 * @param fileId 文件id
	 * @return 外部系统可以直接访问的url
	 * @date 2020/10/26 10:40
	 */
	@GetMapping(path = "/client/sys-file/get-file-auth-url")
	String getFileAuthUrl(@RequestParam("fileId") Long fileId);

	/**
	 * 获取文件的下载地址（带鉴权的），生成外网地址
	 * @param fileId 文件id
	 * @param token 用户的token
	 * @return 外部系统可以直接访问的url
	 * @date 2020/10/26 10:40
	 */
	@GetMapping(path = "/client/sys-file/get-file-auth-url-with-token")
	String getFileAuthUrl(@RequestParam("fileId") Long fileId, @RequestParam("token") String token);

	/**
	 * 获取文件的下载地址（不带鉴权的），生成外网地址
	 * @param fileId 文件id
	 * @return 外部系统可以直接访问的url
	 * @date 2020/10/26 10:40
	 */
	@GetMapping(path = "/client/sys-file/get-file-un-auth-url")
	String getFileUnAuthUrl(@RequestParam("fileId") Long fileId);

	/**
	 * 删除文件
	 * @param fileInfoRequest 文件请求
	 */
	@PostMapping(path = "/client/sys-file/delete-really")
	ResponseData<Void> deleteReally(@RequestBody SysFileInfoRequest fileInfoRequest);

}
