/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.controller;

import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.security.api.DragCaptchaApi;
import io.entframework.kernel.security.api.ImageCaptchaApi;
import io.entframework.kernel.security.api.pojo.DragCaptchaImageDTO;
import io.entframework.kernel.security.api.pojo.ImageCaptcha;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 图形验证码
 *
 * @date 2021/1/15 15:11
 */
@RestController
@ApiResource(name = "用户登录图形验证码")
public class KaptchaController {

    @Resource
    private ImageCaptchaApi captchaApi;

    @Resource
    private DragCaptchaApi dragCaptchaApi;

    /**
     * 获取图形验证码
     *
     * @date 2021/7/5 12:00
     */
    @GetResource(name = "获取图形验证码", path = "/captcha", requiredPermission = false, requiredLogin = false)
    public ResponseData<ImageCaptcha> captcha() {
        return ResponseData.ok(captchaApi.captcha());
    }

    /**
     * 获取拖拽验证码
     *
     * @date 2021/7/5 12:00
     */
    @GetResource(name = "获取图形验证码", path = "/drag-captcha", requiredPermission = false, requiredLogin = false)
    public ResponseData<DragCaptchaImageDTO> dragCaptcha() {
        DragCaptchaImageDTO captcha = dragCaptchaApi.createCaptcha();
        captcha.setSrcImage(RuleConstants.BASE64_IMG_PREFIX + captcha.getSrcImage());
        captcha.setCutImage(RuleConstants.BASE64_IMG_PREFIX + captcha.getCutImage());
        return ResponseData.ok(captcha);
    }

}
