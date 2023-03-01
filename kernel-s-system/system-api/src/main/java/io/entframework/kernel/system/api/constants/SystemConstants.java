/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.constants;

/**
 * 系统管理模块的常量
 *
 * @date 2020/11/4 15:48
 */
public interface SystemConstants {

	/**
	 * 系统管理模块的名称
	 */
	String SYSTEM_MODULE_NAME = "kernel-s-system";

	/**
	 * flyway 表后缀名
	 */
	String FLYWAY_TABLE_SUFFIX = "_system";

	/**
	 * flyway 脚本存放位置
	 */
	String FLYWAY_LOCATIONS = "classpath:kernel_schema/system";

	/**
	 * 异常枚举的步进值
	 */
	String SYSTEM_EXCEPTION_STEP_CODE = "18";

	/**
	 * 默认的系统版本号
	 */
	String DEFAULT_SYSTEM_VERSION = "20210101";

	/**
	 * 默认多租户的开关：关闭
	 */
	Boolean DEFAULT_TENANT_OPEN = false;

	/**
	 * 默认的系统的名称
	 */
	String DEFAULT_SYSTEM_NAME = "Guns快速开发平台";

	/**
	 * 超级管理员的角色编码
	 */
	String SUPER_ADMIN_ROLE_CODE = "superAdmin";

	/**
	 * 初始化超级管理员的监听器顺序
	 */
	Integer SUPER_ADMIN_INIT_LISTENER_SORT = 400;

	/**
	 * 主题编码相关的系统变量前缀
	 */
	String THEME_CODE_SYSTEM_PREFIX = "GUNS";

	/**
	 * 系统内置主题模板的编码
	 */
	String THEME_GUNS_PLATFORM = "GUNS_PLATFORM";

}
