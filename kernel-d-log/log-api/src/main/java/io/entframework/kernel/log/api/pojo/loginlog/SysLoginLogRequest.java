/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.api.pojo.loginlog;

import io.entframework.kernel.log.api.enums.LoginEventType;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 登录日志表
 *
 * @date 2021/1/13 11:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class SysLoginLogRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "llgId不能为空", groups = { detail.class })
    private Long llgId;

    /**
     * 日志名称
     */
    private String llgName;

    /**
     * 是否执行成功
     */
    private String llgSucceed;

    /**
     * 具体消息
     */
    private String llgMessage;

    /**
     * 登录ip
     */
    private String llgIpAddress;

    /**
     * 用户id
     */
    private Long userId;

    private String loginAccount;

    private LoginEventType type;

    private List<Long> llgIds;

}
