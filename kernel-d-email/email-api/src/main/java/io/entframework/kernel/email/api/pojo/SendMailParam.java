/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.email.api.pojo;

import cn.hutool.setting.Setting;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 发送邮件的请求参数
 *
 * @date 2018-07-05 21:19
 */
@Data
public class SendMailParam {

    /**
     * 收件人列表
     */
    private List<String> tos;

    /**
     * 抄送人列表,可以为null或空
     */
    private List<String> ccsTos;

    /**
     * 密送人列表,可以为null或空
     */
    private List<String> bccsTos;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件列表
     */
    private File[] files;

    /**
     * 图片与占位符，占位符格式为cid:${cid}
     * <p>
     * 注意:只有发送html邮件，图片才可正常显示 如:测试图片1:<img src='cid:image01'>
     */
    private Map<String, InputStream> imageMap;

    /**
     * 发送参数
     */
    private Setting serverSetting;

}
