/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.rule.util.HttpServletUtil;
import io.entframework.kernel.system.api.constants.StatisticsCacheConstants;
import io.entframework.kernel.system.modular.home.context.StatisticsUrlContext;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsUrlResponse;
import io.entframework.kernel.system.modular.home.service.SysStatisticsCountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 接口统计的AOP
 *
 * @date 2022/2/10 9:56
 */
@Slf4j
@Aspect
public class InterfaceStatisticsAop implements Ordered {

	@Resource(name = "requestCountCacheApi")
	private CacheOperatorApi<Map<Long, Integer>> requestCountCacheApi;

	@Resource
	private SysStatisticsCountService sysStatisticsCountService;

	/**
	 * 切所有控制器方法
	 *
	 * @date 2022/2/10 20:25
	 */
	@Pointcut("execution(* *..controller.*.*(..))")
	public void flowControl() {
	}

	/**
	 * 具体切面逻辑
	 *
	 * @date 2022/2/10 20:25
	 */
	@Around(value = "flowControl()")
	public Object flowControl(ProceedingJoinPoint joinPoint) throws Throwable {

		// 先执行原有业务
		Object proceed = joinPoint.proceed();

		// 执行统计业务，只统计指定的几个接口
		try {
			saveRequestCount();
		} catch (Exception e) {
			// 统计业务出现异常打印日志
			log.error("接口统计出现异常！", e);
		}

		return proceed;
	}

	/**
	 * 保存接口统计数据，记录当前用户对当前url的访问
	 *
	 * @date 2022/2/10 21:25
	 */
	private void saveRequestCount() {

		// 获取请求的地址
		HttpServletRequest request = HttpServletUtil.getRequest();
		String currentUrl = request.getRequestURI();

		// 获取当前登录用户
		LoginUser loginUserNullable = LoginContext.me().getLoginUserNullable();
		if (loginUserNullable == null) {
			return;
		}

		// 判断当前url是否需要统计
		List<SysStatisticsUrlResponse> urls = StatisticsUrlContext.getUrls();
		if (urls.stream().noneMatch(i -> currentUrl.equals(i.getStatUrl()))) {
			return;
		}

		// 查看当前用户是否有缓存记录
		Long userId = loginUserNullable.getUserId();
		Long statUrlId = StatisticsUrlContext.getStatUrlId(currentUrl);
		Map<Long, Integer> userStatList = null;
		if (!requestCountCacheApi.contains(String.valueOf(userId))) {
			userStatList = new HashMap<>();
		} else {
			userStatList = requestCountCacheApi.get(String.valueOf(userId));
		}

		// 增加这次统计
		Integer urlCount = userStatList.get(statUrlId);
		if (urlCount != null) {
			int newUrlCount = urlCount + 1;
			userStatList.put(statUrlId, newUrlCount);
		} else {
			// 没有缓存就从库里查询这个用户的访问记录
			Integer userUrlCount = sysStatisticsCountService.getUserUrlCount(userId, statUrlId);
			userStatList.put(statUrlId, userUrlCount);
		}

		// 存放到缓存中
		requestCountCacheApi.put(String.valueOf(userId), userStatList,
				StatisticsCacheConstants.INTERFACE_STATISTICS_CACHE_TIMEOUT_SECONDS);
	}

	@Override
	public int getOrder() {
		return StatisticsCacheConstants.INTERFACE_STATISTICS_AOP_ORDER;
	}

}
