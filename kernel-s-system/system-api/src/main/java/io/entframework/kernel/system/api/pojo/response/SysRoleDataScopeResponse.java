/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.api.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色数据范围 服务响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysRoleDataScopeResponse extends BaseResponse {
    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long roleDataScopeId;

    /**
     * 角色id
     */
    @ChineseDescription("角色id")
    private Long roleId;

    /**
     * 机构id
     */
    @ChineseDescription("机构id")
    private Long organizationId;
}