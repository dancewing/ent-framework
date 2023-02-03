/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * 角色资源关联 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleResourceRequest extends BaseRequest {
    /**
     * 主键
     */
    @NotBlank(message = "主键不能为空", groups = {add.class, update.class})
    @ChineseDescription("主键")
    private Long roleResourceId;

    /**
     * 角色id
     */
    @ChineseDescription("角色id")
    private Long roleId;

    /**
     * 资源编码
     */
    @NotBlank(message = "资源编码不能为空", groups = {add.class, update.class})
    @ChineseDescription("资源编码")
    private String resourceCode;
}