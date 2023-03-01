/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.entframework.kernel.cache.memory.operator.DefaultMemoryCacheOperator;
import io.entframework.kernel.security.api.DragCaptchaApi;
import io.entframework.kernel.security.api.ImageCaptchaApi;
import io.entframework.kernel.security.api.constants.CaptchaConstants;
import io.entframework.kernel.security.captcha.DragCaptchaService;
import io.entframework.kernel.security.captcha.ImageCaptchaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 图形验证码自动配置
 *
 * @date 2020/12/1 21:44
 */
@Configuration
public class CaptchaAutoConfiguration {

	/**
	 * 图形验证码
	 *
	 * @date 2021/1/15 11:25
	 */
	@Bean
	@ConditionalOnMissingBean(ImageCaptchaApi.class)
	public ImageCaptchaApi captchaApi() {
		// 验证码过期时间 120秒
		TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * 120);
		DefaultMemoryCacheOperator<String> captchaMemoryCache = new DefaultMemoryCacheOperator<>(timedCache,
				CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX);
		return new ImageCaptchaService(captchaMemoryCache);
	}

	/**
	 * 拖拽验证码工具
	 *
	 * @date 2021/7/5 11:57
	 */
	@Bean
	@ConditionalOnMissingBean(DragCaptchaApi.class)
	public DragCaptchaApi dragCaptchaService() {
		// 验证码过期时间 120秒
		TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * 120);
		DefaultMemoryCacheOperator<String> captchaMemoryCache = new DefaultMemoryCacheOperator<>(timedCache,
				CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX);
		return new DragCaptchaService(captchaMemoryCache);
	}

}
