package io.entframework.kernel.rule.plugin;

/**
 * 模块的元数据
 */
public interface ModuleMeta {

	/**
	 * 模块名称
	 * @return 模块名称
	 */
	String getName();

	/**
	 * 版本信息
	 * @return 版本信息
	 */
	default String getVersion() {
		return "1.0.0";
	}

	/**
	 * 返回flyway 表的后缀
	 * @return flyway 表的后缀
	 */
	default String getFlywayTableSuffix() {
		return null;
	}

	/**
	 * 返回flyway 脚本存储位置
	 * @return flyway 脚本存储位置
	 */
	default String getFlywayLocations() {
		return null;
	}

}
