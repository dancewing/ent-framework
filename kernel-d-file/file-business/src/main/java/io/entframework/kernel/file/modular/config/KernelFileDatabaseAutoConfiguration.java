/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.modular.config;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.modular.service.impl.DatabaseFileOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件的自动配置
 *
 * @author jeff_qian
 * @date 2020/12/1 14:34
 */
@Configuration
public class KernelFileDatabaseAutoConfiguration {

	/**
	 * 数据库文件操作
	 *
	 * @date 2020/12/1 14:40
	 */
	@Bean
	@ConditionalOnMissingBean(name = "databaseFileOperator")
	public FileOperatorApi databaseFileOperator() {
		return new DatabaseFileOperator();
	}

}
