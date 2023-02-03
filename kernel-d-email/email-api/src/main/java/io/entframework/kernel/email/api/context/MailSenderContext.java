/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.api.context;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.email.api.MailSenderApi;

/**
 * 邮件发送的api上下文
 *
 * @date 2020/10/26 10:16
 */
public class MailSenderContext {

    /**
     * 获取邮件发送的接口
     *
     * @date 2020/10/26 10:16
     */
    public static MailSenderApi me() {
        return SpringUtil.getBean(MailSenderApi.class);
    }

}
