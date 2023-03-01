/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.i18n.api.constants;

/**
 * 多语言模块的常量
 *
 * @date 2021/1/24 16:36
 */
public interface TranslationConstants {

	/**
	 * 多语言模块的名称
	 */
	String I18N_MODULE_NAME = "kernel-d-i18n";

	/**
	 * flyway 表后缀名
	 */
	String FLYWAY_TABLE_SUFFIX = "_i18n";

	/**
	 * flyway 脚本存放位置
	 */
	String FLYWAY_LOCATIONS = "classpath:kernel_schema/i18n";

	/**
	 * 异常枚举的步进值
	 */
	String I18N_EXCEPTION_STEP_CODE = "25";

	/**
	 * 菜单类型的tranCode前缀
	 */
	String TRAN_CODE_MENU_PREFIX = "MENU_";

}
