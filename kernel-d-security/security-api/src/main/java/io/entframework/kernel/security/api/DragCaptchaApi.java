/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.api;

import io.entframework.kernel.security.api.pojo.DragCaptchaImageDTO;

/**
 * 拖拽验证码
 *
 * @date 2021/7/5 12:05
 */
public interface DragCaptchaApi {

    /**
     * 生成拖拽验证码的返回值
     *
     * @date 2021/7/5 11:55
     */
    DragCaptchaImageDTO createCaptcha();

    /**
     * 验证拖拽验证码
     *
     * @date 2021/7/5 11:55
     */
    boolean validateCaptcha(String verKey, Integer verCode);

}
