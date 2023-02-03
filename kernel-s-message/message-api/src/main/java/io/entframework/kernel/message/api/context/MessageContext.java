/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.message.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.message.api.MessageApi;

/**
 * 消息操作api的获取
 *
 * @date 2021/1/1 21:13
 */
public class MessageContext {

    /**
     * 获取消息操作api
     *
     * @date 2021/1/1 21:13
     */
    public static MessageApi me() {
        return SpringUtil.getBean(MessageApi.class);
    }

}
