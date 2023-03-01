/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.file.api.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件存储信息 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysFileStorageRequest extends BaseRequest {

	/**
	 * 文件主键id，关联file_info表的主键
	 */
	@NotNull(message = "文件主键id，关联file_info表的主键不能为空",
			groups = { update.class, delete.class, detail.class, updateStatus.class })
	@ChineseDescription("文件主键id，关联file_info表的主键")
	private Long fileId;

	/**
	 * 具体文件的字节信息
	 */
	@ChineseDescription("具体文件的字节信息")
	private byte[] fileBytes;

	@NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
	@ChineseDescription("ID集合")
	private java.util.List<Long> fileIds;

}