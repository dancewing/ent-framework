/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.expander;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import io.entframework.kernel.config.api.context.ConfigContext;
import io.entframework.kernel.security.api.constants.SecurityConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全模块的配置
 *
 * @date 2021/2/19 8:49
 */
public class SecurityConfigExpander {

    /**
     * 获取XSS过滤的url范围
     *
     * @date 2021/1/13 23:21
     */
    public static String[] getUrlPatterns() {
        String xssUrlIncludes = ConfigContext.me().getSysConfigValueWithDefault("SYS_XSS_URL_INCLUDES", String.class, SecurityConstants.DEFAULT_XSS_PATTERN);
        List<String> split = CharSequenceUtil.split(xssUrlIncludes, ',');
        return ArrayUtil.toArray(split, String.class);
    }

    /**
     * 获取XSS排除过滤的url范围
     *
     * @date 2021/1/13 23:21
     */
    public static List<String> getUrlExclusion() {
        String noneSecurityUrls = ConfigContext.me().getSysConfigValueWithDefault("SYS_XSS_URL_EXCLUSIONS", String.class, "");
        if (CharSequenceUtil.isEmpty(noneSecurityUrls)) {
            return new ArrayList<>();
        } else {
            return CharSequenceUtil.split(noneSecurityUrls, ',');
        }
    }

    /**
     * 获取AES秘钥
     *
     * @return {@link String}
     * @date 2021/7/5 10:15
     **/
    public static String getEncryptSecretKey() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ENCRYPT_SECRET_KEY", String.class, "Ux1dqQ22KxVjSYootgzMe776em8vWEGE");
    }

    /**
     * 获取验证码的开关
     *
     * @date 2020/12/27 17:22
     */
    public static Boolean getCaptchaOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_CAPTCHA_OPEN", Boolean.class, SecurityConstants.DEFAULT_CAPTCHA_OPEN);
    }

    /**
     * 获取拖拽验证码的开关
     *
     * @date 2020/12/27 17:22
     */
    public static Boolean getDragCaptchaOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRAG_CAPTCHA_OPEN", Boolean.class, SecurityConstants.DEFAULT_CAPTCHA_OPEN);
    }

}
