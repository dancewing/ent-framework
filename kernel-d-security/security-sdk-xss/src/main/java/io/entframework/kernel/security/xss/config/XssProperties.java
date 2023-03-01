/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.xss.config;

import io.entframework.kernel.security.api.constants.SecurityConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Xss的相关配置
 *
 * @author jeff_qian
 * @date 2021/1/13 22:46
 */
@Data
@ConfigurationProperties(prefix = "kernel.security.xss")
public class XssProperties {

    /**
     * 总控开关
     */
    private boolean enabled = true;

    /**
     * xss过滤的servlet范围，用在设置filter的urlPattern
     */
    private String urlPatterns = SecurityConstants.DEFAULT_XSS_PATTERN;

    /**
     * 不被xss过滤的url（ANT风格表达式）
     */
    private List<String> urlExclusion;

}
