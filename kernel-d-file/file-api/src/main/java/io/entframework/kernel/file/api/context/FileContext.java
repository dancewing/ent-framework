/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.file.api.FileOperatorApi;

/**
 * 文件操作api的上下文
 *
 * @date 2020/10/26 10:30
 */
public class FileContext {

	/**
	 * 获取文件操作接口
	 *
	 * @date 2020/10/17 14:30
	 */
	public static FileOperatorApi me() {
		return SpringUtil.getBean(FileOperatorApi.class);
	}

}
