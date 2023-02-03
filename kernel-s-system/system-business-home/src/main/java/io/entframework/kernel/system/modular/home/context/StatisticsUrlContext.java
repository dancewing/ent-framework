/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.entframework.kernel.system.modular.home.pojo.request.SysStatisticsUrlRequest;
import io.entframework.kernel.system.modular.home.pojo.response.SysStatisticsUrlResponse;
import io.entframework.kernel.system.modular.home.service.SysStatisticsUrlService;
import jakarta.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 需要被首页常用功能统计的url集合
 *
 * @date 2022/2/10 21:35
 */
@Component
public class StatisticsUrlContext implements CommandLineRunner {

	@Resource
	private SysStatisticsUrlService sysStatisticsUrlService;

	/**
	 * 需要被统计的url
	 */
	private static List<SysStatisticsUrlResponse> STATISTICS_URLS = new ArrayList<>(10);

	/**
	 * 需要被统计的url集合，key是url，value是stat_url_id
	 */
	private static final Map<String, Long> STATISTICS_KEY_VALUES = new HashMap<>(10);

	/**
	 * 获取需要统计的url集合
	 *
	 * @date 2022/2/10 21:37
	 */
	public static List<SysStatisticsUrlResponse> getUrls() {
		return STATISTICS_URLS;
	}

	/**
	 * 获取url对应的stat_url_id
	 *
	 * @date 2022/2/10 21:37
	 */
	public static Long getStatUrlId(String url) {
		return STATISTICS_KEY_VALUES.get(url);
	}

	/**
	 * 初始化被统计的url集合
	 *
	 * @date 2022/2/10 21:37
	 */
	@Override
	public void run(String... args) throws Exception {
		STATISTICS_URLS = sysStatisticsUrlService.select(new SysStatisticsUrlRequest());
		for (SysStatisticsUrlResponse statisticsUrl : STATISTICS_URLS) {
			STATISTICS_KEY_VALUES.put(statisticsUrl.getStatUrl(), statisticsUrl.getStatUrlId());
		}
	}

}
