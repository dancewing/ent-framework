/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.api;

import io.entframework.kernel.email.api.pojo.SendMailParam;

/**
 * 邮件收发统一接口
 *
 * @date 2020/10/23 17:30
 */
public interface MailSenderApi {

    /**
     * 发送普通邮件
     * @param sendMailParam 发送邮件的参数
     * @date 2020/10/23 17:30
     */
    void sendMail(SendMailParam sendMailParam);

    /**
     * 发送html的邮件
     * @param sendMailParam 发送邮件的参数
     * @date 2020/10/23 17:30
     */
    void sendMailHtml(SendMailParam sendMailParam);

}
