/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.auth.api.pojo.auth;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

/**
 * 登录操作的响应结果
 *
 * @author jeff_qian
 * @date 2020/10/19 14:17
 */
@Data
public class LoginResponse {

    @ChineseDescription("用户主键id")
    private Long userId;

    /**
     * 登录人的token
     */
    @ChineseDescription("登录人的token")
    private String token;

    /**
     * 到期时间
     */
    @ChineseDescription("到期时间")
    private Long expireAt;

    /**
     * 使用单点登录
     */
    @ChineseDescription("使用单点登录")
    private Boolean ssoLogin;

    /**
     * 单点登录的loginCode
     */
    @ChineseDescription("单点登录的loginCode")
    private String ssoLoginCode;

    public LoginResponse() {
    }

    /**
     * 用于单点登录，返回用户loginCode
     *
     * @date 2021/5/25 22:31
     */
    public LoginResponse(String loginCode) {
        this.ssoLogin = true;
        this.ssoLoginCode = loginCode;
    }

}
