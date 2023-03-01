/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.pojo.response;

import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.Data;

/**
 * 文件信息结果集
 *
 * @date 2020/6/7 22:15
 */
@Data
public class SysFileInfoResponse {

	/**
	 * 主键id
	 */
	@ChineseDescription("主键id")
	private Long fileId;

	/**
	 * 文件编码
	 * <p>
	 * 解决一个文件多个版本问题，多次上传文件编码不变
	 * <p>
	 * 版本号升级的依据，code相同id不同视为同一个文件的不同版本
	 */
	@ChineseDescription("文件编码")
	private Long fileCode;

	/**
	 * 文件版本
	 */
	@ChineseDescription("文件版本")
	private Integer fileVersion;

	/**
	 * 文件状态（0-历史版,1-最新版）
	 */
	@ChineseDescription("文件状态")
	private String fileStatus;

	/**
	 * 文件后缀，例如.txt
	 */
	@ChineseDescription("问价后缀")
	private String fileSuffix;

	/**
	 * 文件大小kb
	 */
	@ChineseDescription("文件大小kb")
	private Long fileSizeKb;

	/**
	 * 文件大小信息，计算后的
	 */
	@ChineseDescription("文件大小信息，计算后")
	private String fileSizeInfo;

	/**
	 * 是否为机密文件
	 */
	@ChineseDescription("是否为机密文件")
	private YesOrNotEnum secretFlag;

	/**
	 * 文件的字节
	 */
	@ChineseDescription("文件的字节")
	private byte[] fileBytes;

	/**
	 * 存储到bucket中的名称，主键id+.后缀
	 */
	@ChineseDescription("存储到bucket中的名称，主键id+.后缀")
	private String fileObjectName;

	/**
	 * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
	 */
	@ChineseDescription("文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地")
	private FileStorageEnum fileLocation;

	/**
	 * 文件仓库
	 */
	@ChineseDescription("文件仓库")
	private String fileBucket;

	/**
	 * 文件名称（上传时候的文件名）
	 */
	@ChineseDescription("文件名称（上传时候的文件名）")
	private String fileOriginName;

	/**
	 * 存储路径
	 */
	@ChineseDescription("存储路径")
	private String filePath;

	/**
	 * 文件访问的路径，如果是私密文件，则返回带鉴权的url，如果不是私密文件，则返回公网能直接访问的url
	 */
	@ChineseDescription("文件访问路径")
	private String fileUrl;

}
