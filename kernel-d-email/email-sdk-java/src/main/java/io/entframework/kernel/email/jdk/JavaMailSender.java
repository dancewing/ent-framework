/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.jdk;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import io.entframework.kernel.email.api.MailSenderApi;
import io.entframework.kernel.email.api.config.EmailProperties;
import io.entframework.kernel.email.api.exception.MailException;
import io.entframework.kernel.email.api.exception.enums.EmailExceptionEnum;
import io.entframework.kernel.email.api.pojo.SendMailParam;
import jakarta.annotation.Resource;
import org.springframework.util.Assert;

/**
 * 邮件发送器
 *
 * @date 2020/6/9 22:54
 */
public class JavaMailSender implements MailSenderApi {

    @Resource
    private EmailProperties emailProperties;

    @Override
    public void sendMail(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);
        //spring发送邮件
        MailUtil.send(this.getConfigAccountInfo(sendMailParam), sendMailParam.getTos(), sendMailParam.getCcsTos(), sendMailParam.getBccsTos(), sendMailParam.getTitle(), sendMailParam.getContent(), sendMailParam.getImageMap(), false, sendMailParam.getFiles());
    }

    @Override
    public void sendMailHtml(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);
        //spring发送邮件
        MailUtil.send(this.getConfigAccountInfo(sendMailParam), sendMailParam.getTos(), sendMailParam.getCcsTos(), sendMailParam.getBccsTos(), sendMailParam.getTitle(), sendMailParam.getContent(), sendMailParam.getImageMap(), true, sendMailParam.getFiles());
    }

    /**
     * 获取配置账号信息
     *
     * @return {@link MailAccount}
     * @date 2021/8/16 13:57
     **/
    private MailAccount getConfigAccountInfo(SendMailParam properties) {
        Assert.notNull(properties, "SendMailParam can't be null");

        if (properties.getServerSetting() != null) {
            MailAccount mailAccount = new MailAccount();
            return properties.getServerSetting().toBean(mailAccount);
        } else {
            // 配置默认都从系统配置表获取
            return this.emailProperties.getAccount();
        }
    }

    /**
     * 校验发送邮件的请求参数
     *
     * @date 2018/7/8 下午6:41
     */
    private void assertSendMailParams(SendMailParam sendMailParam) {
        if (sendMailParam == null) {
            String format = CharSequenceUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTos())) {
            String format = CharSequenceUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "收件人邮箱");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTitle())) {
            String format = CharSequenceUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件标题");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getContent())) {
            String format = CharSequenceUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件内容");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }
    }
}
