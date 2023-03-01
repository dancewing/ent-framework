/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.file.entity;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Mongodb 文件存储集合实体
 *
 * @date 2021/03/26 17:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("mongo_file")
public class MongoFileEntity extends BaseRequest {

	/**
	 * 集合id
	 */
	@Id
	private String id;

	/**
	 * 文件名称
	 */
	private String name;

	/**
	 * 上传文件日期
	 */
	private Date uploadDate;

	/**
	 * 上传文件用户编号
	 */
	private Long uploadUserId;

	/**
	 * 文件后缀名
	 */
	private String suffix;

	/**
	 * 文件描述
	 */
	private String description;

	/**
	 * Mongodb GridFS 中 fs.files集合编号
	 */
	private String gridfsId;

	/**
	 * 下载文件的 响应字段
	 */
	private byte[] content;

}
