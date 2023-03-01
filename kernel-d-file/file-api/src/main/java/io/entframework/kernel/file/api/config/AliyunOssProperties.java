/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云cos文件存储配置
 *
 * @date 2020/10/26 10:50
 */
@Data
@ConfigurationProperties(prefix = "kernel.file.ali-oss")
public class AliyunOssProperties {

	/**
	 * 默认北京，内网
	 * <p>
	 * https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.2.17.467f45dcjB4WQQ#concept-zt4-cvy-5db
	 */
	private String endPoint = "http://oss-cn-beijing.aliyuncs.com";

	/**
	 * Bucket
	 */
	private String bucket;

	/**
	 * 秘钥id
	 */
	private String accessKeyId;

	/**
	 * 秘钥secret
	 */
	private String accessKeySecret;

}
