/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.expander;

import io.entframework.kernel.config.api.context.ConfigContext;
import io.entframework.kernel.message.api.constants.MessageConstants;

/**
 * websocket相关配置快速获取
 *
 * @date 2021/1/25 20:05
 */
public class WebSocketConfigExpander {

    /**
     * 获取websocket的ws-url
     *
     * @date 2021/1/25 20:34
     */
    public static String getWebSocketWsUrl() {
        return ConfigContext.me().getSysConfigValueWithDefault("WEB_SOCKET_WS_URL", String.class, MessageConstants.DEFAULT_WS_URL);
    }

}
