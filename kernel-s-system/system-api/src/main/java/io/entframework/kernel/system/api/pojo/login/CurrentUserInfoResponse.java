/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.pojo.login;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 登录人详细信息
 *
 * @date 2021/3/22 21:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserInfoResponse {

    /**
     * 用户主键id
     */
    @ChineseDescription("用户主键id")
    private Long userId;

    /**
     * 公司/组织id
     */
    @ChineseDescription("公司/组织id")
    private Long organizationId;

    /**
     * 登录人的ws-url
     */
    @ChineseDescription("登录人的ws-url")
    private String wsUrl;

    /**
     * 昵称
     */
    @ChineseDescription("昵称")
    private String nickname;

    /**
     * 用户姓名
     */
    @ChineseDescription("用户姓名")
    private String realName;

    /**
     * 用户头像（url）
     */
    @ChineseDescription("用户头像（url）")
    private String avatar;

    /**
     * 用户拥有的资源权限
     */
    @ChineseDescription("用户拥有的资源权限")
    private Set<String> authorities;

    /**
     * 用户拥有的角色编码
     */
    @ChineseDescription("用户拥有的角色编码")
    private Set<String> roles;

}
