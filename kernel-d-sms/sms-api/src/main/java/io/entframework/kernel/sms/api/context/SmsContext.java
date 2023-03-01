/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.sms.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.sms.api.SmsSenderApi;

/**
 * 短信发送类快速获取
 *
 * @date 2020/10/26 16:53
 */
public class SmsContext {

	/**
	 * 获取短信发送接口
	 *
	 * @date 2020/10/26 16:54
	 */
	public static SmsSenderApi me() {
		return SpringUtil.getBean(SmsSenderApi.class);
	}

}
