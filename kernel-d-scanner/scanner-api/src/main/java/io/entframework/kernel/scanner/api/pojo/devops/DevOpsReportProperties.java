/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.scanner.api.pojo.devops;

import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 资源向DevOps平台汇总，需要进行的配置
 *
 * @author jeff_qian
 * @date 2022/1/11 14:29
 */
@Data
@ConfigurationProperties(prefix = ScannerConstants.DEVOPS_REPORT_PREFIX)
public class DevOpsReportProperties {

	/**
	 * DevOps平台的服务端地址，例如：http://127.0.0.1:8087
	 */
	private String serverHost;

	/**
	 * 当前项目在DevOps平台的唯一标识，由DevOps平台颁发
	 */
	private String projectUniqueCode;

	/**
	 * 当前项目和DevOps平台的交互秘钥（jwt秘钥）
	 */
	private String projectInteractionSecretKey;

	/**
	 * Token的有效期
	 */
	private Long tokenValidityPeriodSeconds;

	/**
	 * FieldMetadata类的全路径
	 * <p>
	 * 默认是cn.stylefeng.roses开头的
	 */
	private String fieldMetadataClassPath = ScannerConstants.FIELD_METADATA_CLASS_ALL_PATH;

}
