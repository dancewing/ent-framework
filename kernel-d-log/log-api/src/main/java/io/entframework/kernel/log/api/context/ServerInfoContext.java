/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api.context;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.CharSequenceUtil;

/**
 * 临时缓存服务器信息
 *
 * @date 2020/10/27 17:53
 */
public class ServerInfoContext {

    /**
     * 服务器IP
     */
    private static String serverIp;

    /**
     * 禁止new创建
     */
    private ServerInfoContext() {
    }

    /**
     * 获取server的ip
     *
     * @date 2020/10/27 17:56
     */
    public static String getServerIp() {
        if (CharSequenceUtil.isEmpty(serverIp)) {
            serverIp = NetUtil.getLocalhostStr();
        }
        return serverIp;
    }

}
