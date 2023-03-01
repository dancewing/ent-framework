/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.clean;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.config.api.ConfigInitCallbackApi;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.pojo.theme.DefaultTheme;

/**
 * 系统初始化之后的主题资源刷新
 *
 * @date 2022/2/26 12:55
 */
@Component
public class ThemeConfigRefresh implements ConfigInitCallbackApi {

    @Resource(name = "themeCacheApi")
    private CacheOperatorApi<DefaultTheme> themeCacheApi;

    @Override
    public void initBefore() {

    }

    @Override
    public void initAfter() {
        themeCacheApi.remove(SystemConstants.THEME_GUNS_PLATFORM);
    }

}
