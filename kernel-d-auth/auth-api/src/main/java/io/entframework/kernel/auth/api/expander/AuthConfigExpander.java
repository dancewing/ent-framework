/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.expander;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.config.api.context.ConfigContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限相关配置快速获取
 *
 * @date 2020/10/17 16:10
 */
public class AuthConfigExpander {

	/**
	 * 获取不被权限控制的url
	 *
	 * @date 2020/10/17 16:12
	 */
	public static List<String> getNoneSecurityConfig() {
		String noneSecurityUrls = ConfigContext.me().getSysConfigValueWithDefault("SYS_NONE_SECURITY_URLS",
				String.class, "");
		if (CharSequenceUtil.isEmpty(noneSecurityUrls)) {
			return new ArrayList<>();
		}
		else {
			return CharSequenceUtil.split(noneSecurityUrls, ',');
		}
	}

	/**
	 * 获取单账号单端登录的开关
	 * <p>
	 * 单账号单端登录为限制一个账号多个浏览器登录
	 * @return true-开启单端限制，false-关闭单端限制
	 * @date 2020/10/21 14:31
	 */
	public static boolean getSingleAccountLoginFlag() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_SINGLE_ACCOUNT_LOGIN_FLAG", Boolean.class, false);
	}

	/**
	 * 默认解析jwt的秘钥（用于解析sso传过来的token）
	 *
	 * @date 2021/5/25 22:39
	 */
	public static String getSsoJwtSecret() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_JWT_SECRET", String.class,
				AuthConstants.SYS_AUTH_SSO_JWT_SECRET);
	}

	/**
	 * 默认解析sso加密的数据的秘钥
	 *
	 * @date 2021/5/25 22:39
	 */
	public static String getSsoDataDecryptSecret() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_DECRYPT_DATA_SECRET", String.class,
				AuthConstants.SYS_AUTH_SSO_DECRYPT_DATA_SECRET);
	}

	/**
	 * 获取是否开启sso远程会话校验，当系统对接sso后，如需同时校验sso的会话是否存在则开启此开关
	 * @return true-开启远程校验，false-关闭远程校验
	 * @date 2021/5/25 22:39
	 */
	public static Boolean getSsoSessionValidateSwitch() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH", Boolean.class,
				AuthConstants.SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH);
	}

	/**
	 * sso会话远程校验，redis的host
	 *
	 * @date 2021/5/25 22:39
	 */
	public static String getSsoSessionValidateRedisHost() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST", String.class,
				AuthConstants.SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST);
	}

	/**
	 * sso会话远程校验，redis的端口
	 *
	 * @date 2021/5/25 22:39
	 */
	public static Integer getSsoSessionValidateRedisPort() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT",
				Integer.class, AuthConstants.SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT);
	}

	/**
	 * sso会话远程校验，redis的密码
	 *
	 * @date 2021/5/25 22:39
	 */
	public static String getSsoSessionValidateRedisPassword() {
		return ConfigContext.me().getConfigValueNullable("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PASSWORD", String.class);
	}

	/**
	 * sso会话远程校验，redis的db
	 *
	 * @date 2021/5/25 22:39
	 */
	public static Integer getSsoSessionValidateRedisDbIndex() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX",
				Integer.class, AuthConstants.SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX);
	}

	/**
	 * sso会话远程校验，redis的缓存前缀
	 *
	 * @date 2021/5/25 22:39
	 */
	public static String getSsoSessionValidateRedisCachePrefix() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX",
				String.class, AuthConstants.SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX);
	}

	/**
	 * 获取SSO服务器的地址
	 *
	 * @date 2021/5/25 22:39
	 */
	public static String getSsoUrl() {
		return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_HOST", String.class,
				AuthConstants.SYS_AUTH_SSO_HOST);
	}

}
