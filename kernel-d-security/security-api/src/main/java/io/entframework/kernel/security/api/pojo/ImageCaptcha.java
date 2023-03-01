/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api.pojo;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Builder;
import lombok.Data;

/**
 * EasyCaptcha 图形验证码参数
 *
 * @date 2020/8/17 21:43
 */
@Data
@Builder
public class ImageCaptcha {

    /**
     * 缓存Key
     */
    @ChineseDescription("缓存Key")
    private String verKey;

    /**
     * Base64 图形验证码
     */
    @ChineseDescription("Base64 图形验证码")
    private String verImage;

}
