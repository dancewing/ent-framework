/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.sms.api.expander;

import io.entframework.kernel.config.api.context.ConfigContext;
import io.entframework.kernel.sms.api.constants.SmsConstants;

/**
 * 短信相关的配置拓展
 *
 * @date 2020/10/26 22:09
 */
public class SmsConfigExpander {

    /**
     * 获取短信验证码失效时间（单位：秒）
     * <p>
     * 默认300秒
     *
     * @date 2020/10/26 22:09
     */
    public static Integer getSmsValidateExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SMS_VALIDATE_EXPIRED_SECONDS", Integer.class,
                SmsConstants.DEFAULT_SMS_INVALID_SECONDS);
    }

}
