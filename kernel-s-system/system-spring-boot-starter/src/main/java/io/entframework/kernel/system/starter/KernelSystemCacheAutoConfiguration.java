/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.cache.memory.operator.DefaultMemoryCacheOperator;
import io.entframework.kernel.system.api.constants.StatisticsCacheConstants;
import io.entframework.kernel.system.api.constants.SystemCachesConstants;
import io.entframework.kernel.system.api.pojo.theme.DefaultTheme;
import io.entframework.kernel.system.modular.entity.SysRole;
import io.entframework.kernel.system.modular.entity.SysUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 系统管理缓存的自动配置（默认内存缓存）
 *
 * @date 2021/2/28 10:29
 */
@Configuration
public class KernelSystemCacheAutoConfiguration {

	/**
	 * 用户的缓存，非在线用户缓存，此缓存为了加快查看用户相关操作
	 *
	 * @date 2021/2/28 10:30
	 */
	@Bean
	@ConditionalOnMissingBean(name = "sysUserCacheOperatorApi")
	public CacheOperatorApi<SysUser> sysUserCacheOperatorApi() {
		TimedCache<String, SysUser> sysUserTimedCache = CacheUtil
				.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
		return new DefaultMemoryCacheOperator<>(sysUserTimedCache, SystemCachesConstants.USER_CACHE_PREFIX);
	}

	/**
	 * 用户角色对应的缓存
	 *
	 * @date 2021/7/29 23:00
	 */
	@Bean
	@ConditionalOnMissingBean(name = "userRoleCacheApi")
	public CacheOperatorApi<List<Long>> userRoleCacheApi() {
		TimedCache<String, List<Long>> userRoleCache = CacheUtil
				.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
		return new DefaultMemoryCacheOperator<>(userRoleCache, SystemCachesConstants.USER_ROLES_CACHE_PREFIX);
	}

	/**
	 * 角色信息对应的缓存
	 *
	 * @date 2021/7/29 23:00
	 */
	@Bean
	@ConditionalOnMissingBean(name = "roleInfoCacheApi")
	public CacheOperatorApi<SysRole> roleInfoCacheApi() {
		TimedCache<String, SysRole> roleCache = CacheUtil
				.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
		return new DefaultMemoryCacheOperator<>(roleCache, SystemCachesConstants.ROLE_INFO_CACHE_PREFIX);
	}

	/**
	 * 用户资源绑定的缓存
	 *
	 * @date 2021/7/30 23:29
	 */
	@Bean
	@ConditionalOnMissingBean(name = "roleResourceCacheApi")
	public CacheOperatorApi<List<String>> roleResourceCacheApi() {
		TimedCache<String, List<String>> roleCache = CacheUtil
				.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
		return new DefaultMemoryCacheOperator<>(roleCache, SystemCachesConstants.ROLE_RESOURCE_CACHE_PREFIX);
	}

	/**
	 * 角色绑定的数据范围的缓存
	 *
	 * @date 2021/7/31 17:59
	 */
	@Bean
	@ConditionalOnMissingBean(name = "roleDataScopeCacheApi")
	public CacheOperatorApi<List<Long>> roleDataScopeCacheApi() {
		TimedCache<String, List<Long>> roleCache = CacheUtil
				.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
		return new DefaultMemoryCacheOperator<>(roleCache, SystemCachesConstants.ROLE_DATA_SCOPE_CACHE_PREFIX);
	}

	/**
	 * 主题的缓存
	 *
	 * @date 2021/7/31 17:59
	 */
	@Bean
	@ConditionalOnMissingBean(name = "themeCacheApi")
	public CacheOperatorApi<DefaultTheme> themeCacheApi() {
		TimedCache<String, DefaultTheme> themeCache = CacheUtil.newTimedCache(Long.MAX_VALUE);
		return new DefaultMemoryCacheOperator<>(themeCache, SystemCachesConstants.SYSTEM_THEME_CACHE_PREFIX);
	}

	/**
	 * 接口统计的缓存
	 *
	 * @date 2022/2/9 16:53
	 */
	@Bean
	@ConditionalOnMissingBean(name = "requestCountCacheApi")
	public CacheOperatorApi<Map<Long, Integer>> requestCountCacheApi() {
		TimedCache<String, Map<Long, Integer>> timedCache = CacheUtil
				.newTimedCache(StatisticsCacheConstants.INTERFACE_STATISTICS_CACHE_TIMEOUT_SECONDS);
		return new DefaultMemoryCacheOperator<>(timedCache, StatisticsCacheConstants.INTERFACE_STATISTICS_PREFIX);
	}
}
