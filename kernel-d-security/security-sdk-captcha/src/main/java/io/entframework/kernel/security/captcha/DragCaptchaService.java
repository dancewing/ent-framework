/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.captcha;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.security.api.DragCaptchaApi;
import io.entframework.kernel.security.api.exception.SecurityException;
import io.entframework.kernel.security.api.exception.enums.SecurityExceptionEnum;
import io.entframework.kernel.security.api.pojo.DragCaptchaImageDTO;
import io.entframework.kernel.security.captcha.util.DragCaptchaImageUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 拖拽验证码实现
 *
 * @date 2021/7/5 11:34
 */
@Slf4j
public class DragCaptchaService implements DragCaptchaApi {

	private final CacheOperatorApi<String> cacheOperatorApi;

	public DragCaptchaService(CacheOperatorApi<String> cacheOperatorApi) {
		this.cacheOperatorApi = cacheOperatorApi;
	}

	public DragCaptchaImageDTO createCaptcha() {
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				Base64.decode(DragCaptchaImageUtil.IMAGE_BASE64));) {
			DragCaptchaImageDTO dragCaptchaImageDTO = DragCaptchaImageUtil.getVerifyImage(byteArrayInputStream);

			// 缓存x轴坐标
			String verKey = IdUtil.simpleUUID();
			Integer verValue = dragCaptchaImageDTO.getLocationX();
			cacheOperatorApi.put(verKey, verValue.toString());

			// 清空x轴坐标
			dragCaptchaImageDTO.setKey(verKey);
			// dragCaptchaImageDTO.setLocationX(null);

			return dragCaptchaImageDTO;
		}
		catch (IOException e) {
			throw new SecurityException(SecurityExceptionEnum.CAPTCHA_ERROR);
		}
	}

	public boolean validateCaptcha(String verKey, Integer verScope) {
		if (CharSequenceUtil.isEmpty(verKey)) {
			return false;
		}
		if (verScope == null) {
			return false;
		}

		// 获取缓存中正确的locationX的值
		String locationXString = cacheOperatorApi.get(verKey);
		if (CharSequenceUtil.isEmpty(locationXString)) {
			throw new SecurityException(SecurityExceptionEnum.CAPTCHA_INVALID_ERROR);
		}

		// 获取缓存中存储的范围
		Integer locationX = Convert.toInt(locationXString);
		int beginScope = locationX - 5;
		int endScope = locationX + 5;

		// 每次验证不管成功和失败都剔除掉key
		cacheOperatorApi.remove(verKey);

		// 验证缓存中的范围值
		return verScope >= beginScope && verScope <= endScope;
	}

}
