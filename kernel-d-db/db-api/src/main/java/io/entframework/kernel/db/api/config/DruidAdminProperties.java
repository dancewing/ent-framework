package io.entframework.kernel.db.api.config;

import cn.hutool.core.util.RandomUtil;
import io.entframework.kernel.db.api.constants.DbConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jeff_qian
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "kernel.druid.admin")
public class DruidAdminProperties {

	/**
	 * 总控开关
	 */
	private boolean enabled = true;

	/**
	 * Druid监控界面的url映射
	 */
	private String urlMappings = DbConstants.DEFAULT_DRUID_URL_MAPPINGS;

	/**
	 * Druid控制台账号
	 */
	private String account = DbConstants.DEFAULT_DRUID_ADMIN_ACCOUNT;

	/**
	 * Druid控制台账号密码
	 */
	private String password;

	/**
	 * Druid控制台的监控数据是否可以重置清零
	 */
	private boolean enableReset = DbConstants.DEFAULT_DRUID_ADMIN_RESET_ENABLE;

	/**
	 * Druid web url统计的拦截范围
	 */
	private String webStatFilterUrlPattern = DbConstants.DRUID_WEB_STAT_FILTER_URL_PATTERN;

	/**
	 * druid web url统计的排除拦截表达式
	 */
	private String webStatFilterExclusions = DbConstants.DRUID_WEB_STAT_FILTER_EXCLUSIONS;

	/**
	 * druid web url统计的session统计开关
	 */
	private boolean enableWebStatFilterSessionStat = DbConstants.DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE;

	/**
	 * druid web url统计的session名称
	 */
	private String webStatFilterSessionName = DbConstants.DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME;

	/**
	 * druid web url统计的session最大监控数
	 */
	private int webStatFilterSessionStatMaxCount = DbConstants.DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT;

	/**
	 * druid web url统计的cookie名称
	 */
	private String webStatFilterPrincipalCookieName = DbConstants.DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME;

	/**
	 * druid web url统计的是否开启监控单个url调用的sql列表
	 */
	private boolean enableWebStatFilterProfile = DbConstants.DRUID_WEB_STAT_FILTER_PROFILE_ENABLE;

	public String getPassword() {
		// 没配置就返回一个随机密码
		if (this.password == null) {
			String randomString = RandomUtil.randomString(20);
			log.info("Druid密码未在系统配置表设置，Druid密码为：{}", randomString);
			return randomString;
		}
		else {
			return this.password;
		}
	}

}
