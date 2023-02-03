/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.log.api.pojo.manage;

import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日志管理的查询参数
 *
 * @date 2020/10/28 11:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogRequest extends BaseRequest {

    /**
     * 单条日志id
     */
    @NotNull(message = "日志id不能为空", groups = {detail.class})
    private Long logId;

    /**
     * 查询的起始时间
     */
    @NotBlank(message = "起始时间不能为空", groups = {delete.class})
    private String beginDate;

    /**
     * 查询日志的结束时间
     */
    @NotBlank(message = "结束时间不能为空", groups = {delete.class})
    private String endDate;

    /**
     * 日志的名称，一般为业务名称
     */
    private String logName;

    /**
     * 服务名称，一般为spring.application.name
     */
    @NotBlank(message = "服务名称不能为空", groups = {delete.class})
    private String appName;

    /**
     * 当前服务器的ip
     */
    private String serverIp;

    /**
     * 客户端请求的用户id
     */
    private Long userId;

    /**
     * 客户端的ip
     */
    private String clientIp;

    /**
     * 当前用户请求的url
     */
    private String requestUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人账号
     */
    private String createUserName;

    private List<Long> logIds;
}
