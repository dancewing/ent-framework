/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.expander;

import io.entframework.kernel.auth.api.constants.AuthConstants;
import io.entframework.kernel.config.api.context.ConfigContext;
import io.entframework.kernel.system.api.constants.SystemConstants;

/**
 * 系统的一些基础信息
 *
 * @date 2020/12/27 17:13
 */
public class SystemConfigExpander {

    private SystemConfigExpander() {
    }

    /**
     * 获取系统发布的版本号（防止css和js的缓存）
     *
     * @date 2020/12/27 17:14
     */
    public static String getReleaseVersion() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_RELEASE_VERSION", String.class, SystemConstants.DEFAULT_SYSTEM_VERSION);
    }

    /**
     * 获取租户是否开启的标识，默认是关的
     *
     * @date 2020/12/27 17:21
     */
    public static Boolean getTenantOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_TENANT_OPEN", Boolean.class, SystemConstants.DEFAULT_TENANT_OPEN);
    }

    /**
     * 获取系统名称
     *
     * @date 2020/12/27 17:22
     */
    public static String getSystemName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SYSTEM_NAME", String.class, SystemConstants.DEFAULT_SYSTEM_NAME);
    }

    /**
     * 获取默认密码
     *
     * @date 2020/11/6 10:05
     */
    public static String getDefaultPassWord() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEFAULT_PASSWORD", String.class, AuthConstants.DEFAULT_PASSWORD);
    }

    /**
     * 获取开发开关的状态
     *
     * @return {@link Boolean}
     * @date 2022/1/17 14:59
     **/
    public static boolean getDevSwitchStatus() {
        Boolean result = ConfigContext.me().getSysConfigValueWithDefault("DEVOPS_DEV_SWITCH_STATUS", Boolean.class, Boolean.FALSE);
        if (result != null) {
            return result;
        }
        return false;
    }
}
