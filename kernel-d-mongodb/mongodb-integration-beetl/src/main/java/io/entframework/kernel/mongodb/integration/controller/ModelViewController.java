/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.mongodb.integration.controller;

import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * MongoDB文件管理界面渲染控制器
 *
 * @date 2021/03/30 15:21
 */
@Controller
@ApiResource(name = "MongoDB文件管理界面渲染控制器")
public class ModelViewController {

	/**
	 * 文件管理视图
	 *
	 * @date 2021/03/30 15:21
	 */
	@GetResource(name = "Mongodb文件列表视图", path = "/view/mongodb/file")
	public String mongodbFile() {
		return "/modular/mongodb/fileList.html";
	}

}
