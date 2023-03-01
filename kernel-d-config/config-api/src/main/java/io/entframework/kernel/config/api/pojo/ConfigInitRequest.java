/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.config.api.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.Map;

/**
 * 初始化系统配置参数
 *
 * @date 2021/7/8 16:38
 */
@Data
public class ConfigInitRequest {

	/**
	 * 系统配置集合
	 * <p>
	 * key是配置编码，value是配置值
	 */
	@ChineseDescription("系统配置集合")
	private Map<String, String> sysConfigs;

}
