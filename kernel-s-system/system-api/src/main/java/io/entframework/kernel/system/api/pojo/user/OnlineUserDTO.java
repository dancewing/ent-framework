/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.system.api.pojo.user;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.Date;


/**
 * 当前的在线用户的信息封装
 *
 * @date 2021/1/11 22:30
 */
@Data
public class OnlineUserDTO {

    /**
     * 用户的token
     */
    @ChineseDescription("用户的token")
    private String token;

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long userId;

    /**
     * 账号
     */
    @ChineseDescription("账号")
    private String account;

    /**
     * 昵称
     */
    @ChineseDescription("昵称")
    private String nickName;

    /**
     * 姓名
     */
    @ChineseDescription("姓名")
    private String realName;

    /**
     * 性别
     */
    @ChineseDescription("性别")
    private String sex;

    /**
     * 角色名称
     */
    @ChineseDescription("角色名称")
    private String roleName;

    /**
     * 登录的时间
     */
    @ChineseDescription("登录的时间")
    private Date loginTime;

}
