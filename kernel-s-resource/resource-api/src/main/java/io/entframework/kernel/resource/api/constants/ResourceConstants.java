package io.entframework.kernel.resource.api.constants;

public interface ResourceConstants {

	/**
	 * resource模块的名称
	 */
	String RESOURCE_MODULE_NAME = "kernel-s-resource";

	/**
	 * flyway 表后缀名
	 */
	String FLYWAY_TABLE_SUFFIX = "_res";

	/**
	 * flyway 脚本存放位置
	 */
	String FLYWAY_LOCATIONS = "classpath:kernel_schema/res";

	/**
	 * Resource cache bean name
	 */
	String RESOURCE_CACHE_BEAN_NAME = "resourceCache";

}
